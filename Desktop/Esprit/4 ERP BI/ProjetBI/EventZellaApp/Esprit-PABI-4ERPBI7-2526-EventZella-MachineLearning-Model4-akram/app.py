from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

# ==============================
# 📦 LOAD MODELS
# ==============================

try:
    model = joblib.load("fidelity_model.pkl")   # classification
    rf_model = joblib.load("rf_regression.pkl")
    kmeans_model = joblib.load("kmeans.pkl")
    iforest_model = joblib.load("iforest.pkl")
    arima_model = joblib.load("arima.pkl")
    ets_model = joblib.load("ets.pkl")

    print("✅ All models loaded successfully")

except Exception as e:
    print("❌ Error loading models:", e)


# ==============================
# 🔄 FEATURE ADAPTER (🔥 IMPORTANT)
# ==============================

def adapt_features(df):
    """
    Convertit les nouvelles features (n8n)
    vers les anciennes utilisées par le modèle
    """

    df = df.copy()

    # 🔁 mapping vers ancien modèle
    df = df.rename(columns={
        "price": "avg_price",
        "marketing_spend": "avg_marketing",
        "trend_score": "avg_trend",
        "rating": "avg_rating"
    })

    # 🔥 récupérer les features attendues par le modèle
    expected_cols = model.feature_names_in_

    # ajouter colonnes manquantes
    for col in expected_cols:
        if col not in df.columns:
            df[col] = 0

    # garder uniquement les bonnes colonnes
    df = df[expected_cols]

    return df


# ==============================
# 🎯 MAIN ENDPOINT
# ==============================

@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json() or []
        model_type = request.args.get("model")

        # 🔥 JSON → DataFrame robuste
        if isinstance(data, dict):
            df = pd.DataFrame([data])
        else:
            df = pd.DataFrame(data)

        df = df.fillna(0)

        # ==============================
        # 🔵 CLASSIFICATION
        # ==============================
        if model_type == "classification":

            df_model = adapt_features(df)

            pred = model.predict(df_model)

            # probabilité si dispo
            if hasattr(model, "predict_proba"):
                probs = model.predict_proba(df_model)[:, 1]
            else:
                probs = [None] * len(pred)

            return jsonify([
                {
                    "prediction": int(p),
                    "probability": None if probs[i] is None else float(probs[i]),
                    "label": "Loyal Customer" if p == 1 else "Not Loyal"
                }
                for i, p in enumerate(pred)
            ])

        # ==============================
        # 🟢 REGRESSION
        # ==============================
        elif model_type == "regression":

            pred = rf_model.predict(df)

            return jsonify([
                {"predicted_price": float(p)} for p in pred
            ])

        # ==============================
        # 🟣 CLUSTERING
        # ==============================
        elif model_type == "clustering":

            cluster = kmeans_model.predict(df)

            return jsonify([
                {"cluster": int(c)} for c in cluster
            ])

        # ==============================
        # 🔴 ANOMALY DETECTION
        # ==============================
        elif model_type == "anomaly":

            anomaly = iforest_model.predict(df)

            return jsonify([
                {"anomaly": "Yes" if a == -1 else "No"} for a in anomaly
            ])

        # ==============================
        # 🟡 TIME SERIES
        # ==============================
        elif model_type == "timeseries":

            steps = int(request.args.get("steps", 5))

            arima_forecast = arima_model.forecast(steps=steps)
            ets_forecast = ets_model.forecast(steps=steps)

            return jsonify([
                {
                    "step": i + 1,
                    "arima_forecast": float(arima_forecast[i]),
                    "ets_forecast": float(ets_forecast[i])
                }
                for i in range(steps)
            ])

        else:
            return jsonify({"error": "Invalid model type"}), 400

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# ==============================
# 🟢 HEALTH CHECK
# ==============================

@app.route("/")
def home():
    return "🚀 ML API is running!"


# ==============================
# ▶️ RUN SERVER
# ==============================

if __name__ == "__main__":
    app.run(debug=True, port=5000)