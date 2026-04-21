import os
import warnings

from flask import Flask, jsonify, request
import joblib
import numpy as np
import pandas as pd
from sklearn.ensemble import IsolationForest
from sklearn.metrics import mean_absolute_error, mean_squared_error
from sklearn.neighbors import LocalOutlierFactor
from sklearn.preprocessing import StandardScaler
from sklearn.exceptions import InconsistentVersionWarning
from statsmodels.tsa.arima.model import ARIMA
from statsmodels.tsa.holtwinters import ExponentialSmoothing

app = Flask(__name__)


def _to_records(body):
    if isinstance(body, list):
        return body
    if isinstance(body, dict):
        if "data" in body and isinstance(body["data"], list):
            return body["data"]
        first_val = next(iter(body.values()), None)
        if isinstance(first_val, list):
            return pd.DataFrame(body).to_dict(orient="records")
        return [body]
    return []


def _safe_mape(y_true, y_pred):
    y_true_arr = np.array(y_true, dtype=float)
    y_pred_arr = np.array(y_pred, dtype=float)
    denominator = np.where(y_true_arr == 0, np.nan, y_true_arr)
    return float(np.nanmean(np.abs((y_true_arr - y_pred_arr) / denominator)) * 100)


# ==============================
# 📦 LOAD MODELS
# ==============================
model = None
rf_model = None
kmeans_model = None
iforest_model = None
arima_model = None
ets_model = None

try:
    with warnings.catch_warnings(record=True) as caught:
        warnings.simplefilter("always", InconsistentVersionWarning)
        model = joblib.load("fidelity_model.pkl")
        rf_model = joblib.load("rf_regression.pkl")
        kmeans_model = joblib.load("kmeans.pkl")
        iforest_model = joblib.load("iforest.pkl")
        arima_model = joblib.load("arima.pkl")
        ets_model = joblib.load("ets.pkl")

    version_warnings = [
        w for w in caught if isinstance(w.message, InconsistentVersionWarning)
    ]
    if version_warnings:
        print(
            "Warning: model files were saved with a different scikit-learn version. "
            "Predictions may be unstable. Prefer sklearn==1.5.1 or re-export models "
            "with the current version."
        )

    print("All models loaded successfully")
except Exception as e:
    print("Error loading one or more models:", e)


def get_data_from_db():
    try:
        import pyodbc
    except ModuleNotFoundError as e:
        raise ValueError(
            "pyodbc is not installed. Install it to use DB-backed forecasting."
        ) from e

    db_driver = os.getenv("DB_DRIVER", "ODBC Driver 17 for SQL Server")
    db_server = os.getenv("DB_SERVER")
    db_name = os.getenv("DB_NAME")
    db_user = os.getenv("DB_USER")
    db_password = os.getenv("DB_PASSWORD")
    trust_cert = os.getenv("DB_TRUST_SERVER_CERTIFICATE", "yes")

    if not all([db_server, db_name, db_user, db_password]):
        raise ValueError(
            "Missing database configuration. Set DB_SERVER, DB_NAME, DB_USER and DB_PASSWORD."
        )

    conn_str = (
        f"DRIVER={{{db_driver}}};"
        f"SERVER={db_server};"
        f"DATABASE={db_name};"
        f"UID={db_user};"
        f"PWD={db_password};"
        f"TrustServerCertificate={trust_cert};"
    )

    query = """
        SELECT
            FORMAT(CAST(CAST(Date_PK AS VARCHAR(8)) AS DATE), 'yyyy-MM') AS month,
            SUM(nbr_reservations) AS nbr_reservations
        FROM FACT_VENTES
        WHERE Date_PK IS NOT NULL
        GROUP BY FORMAT(CAST(CAST(Date_PK AS VARCHAR(8)) AS DATE), 'yyyy-MM')
        ORDER BY month ASC
    """

    conn = pyodbc.connect(conn_str)
    try:
        df = pd.read_sql(query, conn)
    finally:
        conn.close()

    return df.to_dict(orient="records")


# ==============================
# 🔄 FEATURE ADAPTER
# ==============================
def adapt_features(df):
    if model is None:
        raise ValueError("Classification model is not loaded")

    df = df.copy()
    df = df.rename(
        columns={
            "price": "avg_price",
            "marketing_spend": "avg_marketing",
            "trend_score": "avg_trend",
            "rating": "avg_rating",
        }
    )

    expected_cols = model.feature_names_in_
    for col in expected_cols:
        if col not in df.columns:
            df[col] = 0

    return df[expected_cols]


def run_timeseries(records, steps=6):
    df = pd.DataFrame(records)
    if df.empty:
        raise ValueError("No time series records provided")

    required_cols = {"month", "nbr_reservations"}
    if not required_cols.issubset(df.columns):
        raise ValueError("Time series records must contain 'month' and 'nbr_reservations'")

    df["month"] = pd.to_datetime(df["month"], errors="coerce")
    df = df.dropna(subset=["month", "nbr_reservations"]).sort_values("month")
    df = df.set_index("month")

    series = df["nbr_reservations"].astype(float)
    if len(series) < 8:
        raise ValueError("At least 8 monthly records are required for forecasting")

    train_size = int(len(series) * 0.8)
    if train_size < 4 or len(series) - train_size < 1:
        raise ValueError("Insufficient data split for train/test evaluation")

    train = series.iloc[:train_size]
    test = series.iloc[train_size:]

    best_aic = float("inf")
    best_order = (1, 1, 1)
    for p in range(0, 3):
        for d in range(0, 2):
            for q in range(0, 3):
                try:
                    candidate = ARIMA(train, order=(p, d, q)).fit()
                    if candidate.aic < best_aic:
                        best_aic = candidate.aic
                        best_order = (p, d, q)
                except Exception:
                    continue

    arima_fit = ARIMA(train, order=best_order).fit()
    arima_pred = arima_fit.forecast(steps=len(test))
    arima_pred.index = test.index

    mae_arima = mean_absolute_error(test, arima_pred)
    rmse_arima = float(np.sqrt(mean_squared_error(test, arima_pred)))
    mape_arima = _safe_mape(test, arima_pred)

    ets_available = len(train) >= 24
    if ets_available:
        ets_fit = ExponentialSmoothing(
            train,
            trend="add",
            seasonal="add",
            seasonal_periods=12,
        ).fit()
        ets_pred = ets_fit.forecast(len(test))
        ets_pred.index = test.index
        mae_ets = mean_absolute_error(test, ets_pred)
        rmse_ets = float(np.sqrt(mean_squared_error(test, ets_pred)))
        mape_ets = _safe_mape(test, ets_pred)
    else:
        mae_ets = float("inf")
        rmse_ets = float("inf")
        mape_ets = float("inf")

    best_model = "ARIMA" if mae_arima <= mae_ets else "ETS"
    if best_model == "ARIMA":
        full_fit = ARIMA(series, order=best_order).fit()
        forecast = full_fit.forecast(steps=steps)
    else:
        full_fit = ExponentialSmoothing(
            series,
            trend="add",
            seasonal="add",
            seasonal_periods=12,
        ).fit()
        forecast = full_fit.forecast(steps=steps)

    forecast_list = [
        {
            "month": str(forecast.index[i])[:7],
            "predicted_reservations": int(round(float(forecast.iloc[i]))),
        }
        for i in range(len(forecast))
    ]

    return {
        "best_model": best_model,
        "arima_order": str(best_order),
        "mae_arima": round(float(mae_arima), 2),
        "rmse_arima": round(float(rmse_arima), 2),
        "mape_arima": round(float(mape_arima), 2),
        "mae_ets": None if not np.isfinite(mae_ets) else round(float(mae_ets), 2),
        "rmse_ets": None if not np.isfinite(rmse_ets) else round(float(rmse_ets), 2),
        "mape_ets": None if not np.isfinite(mape_ets) else round(float(mape_ets), 2),
        "forecast_next_months": forecast_list,
    }


def run_anomaly_detection(records):
    df = pd.DataFrame(records)
    if df.empty:
        raise ValueError("No records provided for anomaly detection")

    df.columns = [c.lower().strip() for c in df.columns]
    anomaly_features = [
        "price",
        "nbr_reservations",
        "nbr_visitors",
        "marketing_spend",
        "market_count",
        "rating",
    ]
    available_features = [f for f in anomaly_features if f in df.columns]

    if not available_features:
        raise ValueError(
            f"No matching features found. Received columns: {', '.join(df.columns.tolist())}"
        )

    X = df[available_features].copy()
    X = X.fillna(X.median(numeric_only=True))

    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    best_cont = 0.05
    iforest = IsolationForest(contamination=best_cont, random_state=42, n_estimators=50)
    iforest.fit(X_scaled)

    df["anomaly_iforest"] = iforest.predict(X_scaled)
    df["anomaly_score"] = iforest.decision_function(X_scaled)

    n_neighbors = min(10, max(2, len(df) - 1))
    lof = LocalOutlierFactor(contamination=best_cont, n_neighbors=n_neighbors)
    df["anomaly_lof"] = lof.fit_predict(X_scaled)

    df["anomaly_consensus"] = (
        (df["anomaly_iforest"] == -1) & (df["anomaly_lof"] == -1)
    ).astype(int)

    top10 = df.nsmallest(10, "anomaly_score")[available_features + ["anomaly_score"]].round(2)

    anomalies_df = df[df["anomaly_iforest"] == -1]
    normal_df = df[df["anomaly_iforest"] == 1]

    comparison = []
    for feature in available_features:
        anomalies_mean = float(anomalies_df[feature].mean()) if not anomalies_df.empty else 0.0
        normal_mean = float(normal_df[feature].mean()) if not normal_df.empty else 0.0
        if normal_mean == 0:
            difference_pct = None
        else:
            difference_pct = round(float((anomalies_mean - normal_mean) / normal_mean * 100), 1)

        comparison.append(
            {
                "feature": feature,
                "mean_anomalies": round(anomalies_mean, 2),
                "mean_normal": round(normal_mean, 2),
                "difference_pct": difference_pct,
            }
        )

    return {
        "total_records": int(len(df)),
        "features_used": available_features,
        "best_contamination": best_cont,
        "isolation_forest": {
            "anomalies_detected": int((df["anomaly_iforest"] == -1).sum()),
            "anomalies_pct": round(float((df["anomaly_iforest"] == -1).sum() / len(df) * 100), 2),
        },
        "lof": {
            "anomalies_detected": int((df["anomaly_lof"] == -1).sum()),
            "anomalies_pct": round(float((df["anomaly_lof"] == -1).sum() / len(df) * 100), 2),
        },
        "consensus": {
            "anomalies_detected": int(df["anomaly_consensus"].sum()),
            "anomalies_pct": round(float(df["anomaly_consensus"].sum() / len(df) * 100), 2),
        },
        "feature_comparison": comparison,
        "top10_suspicious": top10.to_dict(orient="records"),
    }


# ==============================
# 🆕 DEDICATED JSON API ENDPOINTS
# ==============================
@app.route("/api/timeseries/forecast", methods=["POST"])
def timeseries_forecast():
    try:
        body = request.get_json(silent=True) or {}
        steps = int(body.get("steps", request.args.get("steps", 6)))
        use_db = bool(body.get("use_db", False))

        records = []
        if use_db:
            records = get_data_from_db()
        else:
            records = _to_records(body)

        if not records:
            return jsonify({"error": "No data provided. Send data or set use_db=true."}), 400

        result = run_timeseries(records, steps=steps)
        return jsonify(result)
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route("/api/anomalies/detect", methods=["POST"])
def anomalies_detect():
    try:
        body = request.get_json(silent=True) or {}
        records = _to_records(body)
        result = run_anomaly_detection(records)
        return jsonify(result)
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500


# ==============================
# 🎯 MAIN ENDPOINT (LEGACY COMPAT)
# ==============================
@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json(silent=True) or []
        model_type = request.args.get("model")

        if isinstance(data, dict):
            df = pd.DataFrame([data])
        else:
            df = pd.DataFrame(data)

        df = df.fillna(0)

        if model_type == "classification":
            df_model = adapt_features(df)
            pred = model.predict(df_model)
            probs = model.predict_proba(df_model)[:, 1] if hasattr(model, "predict_proba") else [None] * len(pred)
            return jsonify(
                [
                    {
                        "prediction": int(p),
                        "probability": None if probs[i] is None else float(probs[i]),
                        "label": "Loyal Customer" if p == 1 else "Not Loyal",
                    }
                    for i, p in enumerate(pred)
                ]
            )

        if model_type == "regression":
            pred = rf_model.predict(df)
            return jsonify([{"predicted_price": float(p)} for p in pred])

        if model_type == "clustering":
            cluster = kmeans_model.predict(df)
            return jsonify([{"cluster": int(c)} for c in cluster])

        if model_type == "anomaly":
            records = _to_records(data)
            if records:
                return jsonify(run_anomaly_detection(records))
            if iforest_model is None:
                raise ValueError("Anomaly model is not loaded and no records were provided")
            anomaly = iforest_model.predict(df)
            return jsonify([{"anomaly": "Yes" if a == -1 else "No"} for a in anomaly])

        if model_type == "timeseries":
            steps = int(request.args.get("steps", 5))
            records = _to_records(data)

            if records:
                return jsonify(run_timeseries(records, steps=steps))

            if arima_model is None or ets_model is None:
                raise ValueError("Pretrained time series models are not loaded and no records were provided")

            arima_forecast = arima_model.forecast(steps=steps)
            ets_forecast = ets_model.forecast(steps=steps)
            return jsonify(
                [
                    {
                        "step": i + 1,
                        "arima_forecast": float(arima_forecast[i]),
                        "ets_forecast": float(ets_forecast[i]),
                    }
                    for i in range(steps)
                ]
            )

        return jsonify({"error": "Invalid model type"}), 400

    except ValueError as e:
        return jsonify({"error": str(e)}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500


# ==============================
# 🟢 HEALTH CHECK
# ==============================
@app.route("/health", methods=["GET"])
def health():
    return jsonify({"status": "ok", "service": "ml-api"})


if __name__ == "__main__":
    print("App module loaded. Start server with: flask --app app run --debug --port 5000")