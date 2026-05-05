import warnings
from pathlib import Path

import joblib
import numpy as np
import pandas as pd
from sklearn.ensemble import IsolationForest
from sklearn.exceptions import InconsistentVersionWarning
from sklearn.metrics import mean_absolute_error, mean_squared_error
from sklearn.neighbors import LocalOutlierFactor
from sklearn.preprocessing import StandardScaler
from statsmodels.tsa.arima.model import ARIMA
from statsmodels.tsa.holtwinters import ExponentialSmoothing

from app.core.config import (
    DB_DRIVER,
    DB_NAME,
    DB_PASSWORD,
    DB_SERVER,
    DB_TRUST_SERVER_CERTIFICATE,
    DB_USER,
    ML_MODELS_DIR,
)
from app.database import engine, data_engine
from sqlalchemy import text


class MLService:
    def __init__(self):
        self.models_dir = Path(ML_MODELS_DIR)
        self.model = None
        self.rf_model = None
        self.kmeans_model = None
        self.iforest_model = None
        self.arima_model = None
        self.ets_model = None
        self.anomaly_model = None
        self.scaler_model = None
        self._load_models()

    def _load_model_file(self, filename: str):
        return joblib.load(self.models_dir / filename)

    def _load_models(self):
        try:
            with warnings.catch_warnings(record=True) as caught:
                warnings.simplefilter("always", InconsistentVersionWarning)
                self.model = self._load_model_file("fidelity_model.pkl")
                self.rf_model = self._load_model_file("rf_regression.pkl")
                self.kmeans_model = self._load_model_file("kmeans.pkl")
                self.iforest_model = self._load_model_file("iforest.pkl")
                self.arima_model = self._load_model_file("arima.pkl")
                self.ets_model = self._load_model_file("ets.pkl")
                try:
                    # Use the new anomaly_model.pkl if it exists, otherwise fallback to anomaly.pkl
                    if (self.models_dir / "anomaly_model.pkl").exists():
                        combined = self._load_model_file("anomaly_model.pkl")
                        if isinstance(combined, dict):
                            self.anomaly_model = combined.get("model")
                            self.scaler_model = combined.get("scaler")
                            print("Loaded combined anomaly model from anomaly_model.pkl")
                        else:
                            self.anomaly_model = combined
                            self.scaler_model = self._load_model_file("scaler.pkl")
                    else:
                        self.anomaly_model = self._load_model_file("anomaly.pkl")
                        self.scaler_model = self._load_model_file("scaler.pkl")
                except Exception as e:
                    print(f"Could not load custom anomaly models: {e}")

            version_warnings = [
                w for w in caught if isinstance(w.message, InconsistentVersionWarning)
            ]
            if version_warnings:
                print("Warning: model files were exported with a different scikit-learn version")

            print("ML models loaded from", self.models_dir)
        except Exception as exc:
            print("ML model loading error:", exc)

    @staticmethod
    def to_records(body):
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

    @staticmethod
    def _safe_mape(y_true, y_pred):
        y_true_arr = np.array(y_true, dtype=float)
        y_pred_arr = np.array(y_pred, dtype=float)
        denominator = np.where(y_true_arr == 0, np.nan, y_true_arr)
        return float(np.nanmean(np.abs((y_true_arr - y_pred_arr) / denominator)) * 100)

    def _adapt_classification_features(self, df: pd.DataFrame):
        if self.model is None:
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

        expected_cols = self.model.feature_names_in_
        for col in expected_cols:
            if col not in df.columns:
                df[col] = 0

        return df[expected_cols]

    def predict_anomaly_realtime(self, payload: dict):
        if self.anomaly_model is None or self.scaler_model is None:
            raise ValueError("Anomaly model or scaler is not loaded")

        expected_features = ["price", "nbr_reservations", "nbr_visitors", "marketing_spend", "market_count", "rating"]
        data = {f: float(payload.get(f, 0.0)) for f in expected_features}
        
        # Le scaler s'attend à un ordre de colonnes strict selon l'entraînement, on le maintient:
        df = pd.DataFrame([data])
        X_scaled = self.scaler_model.transform(df)
        
        pred = self.anomaly_model.predict(X_scaled)[0]
        score = float(self.anomaly_model.decision_function(X_scaled)[0])
        
        is_anomaly = 1 if pred == -1 else 0
        
        if is_anomaly:
            if data["rating"] < 2.5 and data["nbr_visitors"] > 4000:
                explanation = "Critical Alert: Suspicious correlation between high attendance and very low customer satisfaction. Risk of brand reputation damage."
            elif data["marketing_spend"] > 15000 and data["nbr_reservations"] < 50:
                explanation = "Budget Alert: Abnormally low marketing efficiency. High investment for almost zero return."
            elif data["price"] > 150000 and data["market_count"] < 2:
                explanation = "Strategy Alert: Extremely high price in a very restricted market. Risk of massive rejection."
            else:
                explanation = "Statistical Anomaly: The model detected behavior deviating significantly from historical patterns (Outlier)."
        else:
            explanation = "Normal Behavior: Metrics are consistent with standard historical performance patterns."
            
        print(f"DEBUG Realtime Anomaly - Input: {data} -> Prediction: {is_anomaly} (Score: {score})")
            
        return {
            "is_anomaly": is_anomaly,
            "anomaly_score": round(score, 4),
            "explanation": explanation
        }

    def predict_price_response(self, payload: dict):
        if self.rf_model is None:
            raise ValueError(f"Regression model is not loaded (ML_MODELS_DIR={self.models_dir})")

        df = pd.DataFrame([payload]).fillna(0)
        predicted_price = float(self.rf_model.predict(df)[0])

        visitors = float(payload.get("nbr_visitors", 0) or 0)
        reservations = float(payload.get("nbr_reservations", 0) or 0)
        demand_ratio = reservations / visitors if visitors > 0 else 0.0

        if demand_ratio >= 0.10:
            expected_demand = "high"
            recommended_factor = 1.10
            risk_level = "medium"
            strategic_recommendation = "Strong demand trend. Consider premium pricing with bundles."
        elif demand_ratio >= 0.05:
            expected_demand = "medium"
            recommended_factor = 1.03
            risk_level = "low"
            strategic_recommendation = "Balanced demand. Keep competitive pricing and monitor conversion."
        else:
            expected_demand = "low"
            recommended_factor = 0.95
            risk_level = "high"
            strategic_recommendation = "Low demand. Add promotion and improve campaign targeting."

        confidence_score = round(0.65 + min(0.30, max(0.0, demand_ratio)), 2)

        return {
            "predicted_price": round(predicted_price, 2),
            "recommended_price": round(predicted_price * recommended_factor, 2),
            "confidence_score": confidence_score,
            "expected_demand": expected_demand,
            "risk_level": risk_level,
            "strategic_recommendation": strategic_recommendation,
            "scenarios": {
                "conservative": round(predicted_price * 0.93, 2),
                "balanced": round(predicted_price, 2),
                "aggressive": round(predicted_price * 1.12, 2),
            },
        }

    def run_classification(self, data):
        if self.model is None:
            raise ValueError(f"Classification model is not loaded (ML_MODELS_DIR={self.models_dir})")

        df = pd.DataFrame(data if isinstance(data, list) else [data]).fillna(0)
        df_model = self._adapt_classification_features(df)
        pred = self.model.predict(df_model)
        probs = self.model.predict_proba(df_model)[:, 1] if hasattr(self.model, "predict_proba") else [None] * len(pred)

        return [
            {
                "prediction": int(p),
                "probability": None if probs[i] is None else float(probs[i]),
                "label": "Loyal Customer" if p == 1 else "Not Loyal",
            }
            for i, p in enumerate(pred)
        ]

    def run_clustering(self, data):
        if self.kmeans_model is None:
            raise ValueError(f"Clustering model is not loaded (ML_MODELS_DIR={self.models_dir})")

        df = pd.DataFrame(data if isinstance(data, list) else [data]).fillna(0)
        clusters = self.kmeans_model.predict(df)
        return [{"cluster": int(c)} for c in clusters]

    def run_anomaly_detection(self, records):
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
            raise ValueError("No matching features found")

        x = df[available_features].copy()
        x = x.fillna(x.median(numeric_only=True))

        scaler = StandardScaler()
        x_scaled = scaler.fit_transform(x)

        best_cont = 0.05
        iforest = IsolationForest(contamination=best_cont, random_state=42, n_estimators=50)
        iforest.fit(x_scaled)

        df["anomaly_iforest"] = iforest.predict(x_scaled)
        df["anomaly_score"] = iforest.decision_function(x_scaled)

        n_neighbors = min(10, max(2, len(df) - 1))
        lof = LocalOutlierFactor(contamination=best_cont, n_neighbors=n_neighbors)
        df["anomaly_lof"] = lof.fit_predict(x_scaled)

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

    def get_timeseries_records_from_db(self):
        query = text("""
            SELECT
                FORMAT(CAST(CAST(Date_PK AS VARCHAR(8)) AS DATE), 'yyyy-MM') AS month,
                SUM(nbr_reservations) AS nbr_reservations
            FROM FACT_VENTES
            WHERE Date_PK IS NOT NULL
            GROUP BY FORMAT(CAST(CAST(Date_PK AS VARCHAR(8)) AS DATE), 'yyyy-MM')
            ORDER BY month ASC
        """)

        try:
            with data_engine.connect() as conn:
                df = pd.read_sql(query, conn)
        except Exception as exc:
            print(f"Error fetching timeseries from DB: {exc}")
            return []

        return df.to_dict(orient="records")

    def get_anomaly_records_from_db(self):
        query = text("""
            SELECT TOP 1000
                f.Date_PK,
                CAST(f.price AS FLOAT) as price,
                CAST(f.nbr_reservations AS FLOAT) as nbr_reservations,
                CAST(f.nbr_visitors AS FLOAT) as nbr_visitors,
                CAST(f.marketing_spend AS FLOAT) as marketing_spend,
                CAST(f.market_count AS FLOAT) as market_count,
                CAST(e.rating AS FLOAT) as rating
            FROM FACT_VENTES f
            LEFT JOIN Dim_Evaluation e ON f.id_evaluation = e.id_evaluation
            WHERE f.Date_PK IS NOT NULL
            ORDER BY f.Date_PK DESC
        """)

        with data_engine.connect() as conn:
            df = pd.read_sql(query, conn)

        if df.empty:
            return []
            
        return df.to_dict(orient="records")

    def run_timeseries(self, records, steps=6):
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
        mape_arima = self._safe_mape(test, arima_pred)

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
            mape_ets = self._safe_mape(test, ets_pred)
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

    def run_legacy_predict(self, data, model_type: str, steps: int = 5):
        if isinstance(data, dict):
            df = pd.DataFrame([data])
        else:
            df = pd.DataFrame(data)

        df = df.fillna(0)

        if model_type == "classification":
            return self.run_classification(data)

        if model_type == "regression":
            if self.rf_model is None:
                raise ValueError("Regression model is not loaded")
            pred = self.rf_model.predict(df)
            return [{"predicted_price": float(p)} for p in pred]

        if model_type == "clustering":
            return self.run_clustering(data)

        if model_type == "anomaly":
            records = self.to_records(data)
            if records:
                return self.run_anomaly_detection(records)
            if self.iforest_model is None:
                raise ValueError("Anomaly model is not loaded")
            anomaly = self.iforest_model.predict(df)
            return [{"anomaly": "Yes" if a == -1 else "No"} for a in anomaly]

        if model_type == "timeseries":
            records = self.to_records(data)
            if records:
                return self.run_timeseries(records, steps=steps)

            if self.arima_model is None or self.ets_model is None:
                raise ValueError("Pretrained time series models are not loaded")

            arima_forecast = self.arima_model.forecast(steps=steps)
            ets_forecast = self.ets_model.forecast(steps=steps)
            return [
                {
                    "step": i + 1,
                    "arima_forecast": float(arima_forecast[i]),
                    "ets_forecast": float(ets_forecast[i]),
                }
                for i in range(steps)
            ]

        raise ValueError("Invalid model type")
