from flask import Flask, jsonify
from flask_cors import CORS
from app.database import Base, engine
from app.core.config import CORS_ALLOWED_ORIGINS
from app.routers.auth import auth_api_bp, auth_legacy_bp
from app.routers.ml import ml_bp, legacy_ml_bp
import threading
import time

from flask import request
from app.core.monitoring import (
    REQUEST_COUNT,
    REQUEST_LATENCY,
    API_ERRORS,
    metrics_response,
    update_data_freshness,
)
try:
    Base.metadata.create_all(bind=engine)
    print("✅ Tables créées / vérifiées")
except Exception as e:
    print(f"⚠️ Erreur DB : {e}")

app = Flask(__name__)
def freshness_loop():
    while True:
        update_data_freshness()
        time.sleep(5)

threading.Thread(target=freshness_loop, daemon=True).start()
@app.before_request
def start_timer():
    request.start_time = time.time()


@app.after_request
def record_metrics(response):
    endpoint = request.path
    method = request.method
    status = response.status_code

    REQUEST_COUNT.labels(
        method=method,
        endpoint=endpoint,
        status=status
    ).inc()

    if hasattr(request, "start_time"):
        latency = time.time() - request.start_time
        REQUEST_LATENCY.labels(endpoint=endpoint).observe(latency)

    if status >= 400:
        API_ERRORS.labels(
            endpoint=endpoint,
            error_type=str(status)
        ).inc()

    return response


@app.route("/metrics")
def metrics():
    return metrics_response()
origins = [origin.strip() for origin in CORS_ALLOWED_ORIGINS.split(",") if origin.strip()]

CORS(
    app,
    resources={r"/*": {"origins": origins}},
    supports_credentials=True,
    allow_headers=["Content-Type", "Authorization", "Accept"],
    methods=["GET", "POST", "PUT", "DELETE", "OPTIONS"]
)

app.register_blueprint(auth_api_bp)
app.register_blueprint(auth_legacy_bp)
app.register_blueprint(ml_bp)
app.register_blueprint(legacy_ml_bp)

@app.route("/", methods=["GET"])
def root():
    return jsonify({"message": "Unified BI API", "status": "ok"})