from flask import Blueprint, jsonify, request


from app.services.ml_service import MLService
from app.services.n8n_service import N8nService
import random

from app.core.monitoring import (
    detect_missing_values,
    detect_drift,
    update_model_confidence,
    update_accuracy,
    mark_new_data_received,
    API_ERRORS,
)
ml_bp = Blueprint("ml", __name__, url_prefix="/api/ml")
legacy_ml_bp = Blueprint("ml_legacy", __name__)

ml_service = MLService()
n8n_service = N8nService()


# @ml_bp.route("/predict-price", methods=["POST"])
# def predict_price():
#     try:
#         payload = request.get_json(force=True, silent=True) or {}
#         result = ml_service.predict_price_response(payload)
#         n8n_service.emit_prediction_event({"event": "prediction.generated", "payload": payload, "result": result})
#         return jsonify(result), 200
#     except ValueError as exc:
#         return jsonify({"detail": str(exc)}), 400
#     except Exception as exc:
#         return jsonify({"detail": str(exc)}), 500

@ml_bp.route("/predict-price", methods=["POST"])
def predict_price():
    try:
        payload = request.get_json(force=True, silent=True) or {}
        mark_new_data_received()

        detect_missing_values(payload)
        detect_drift(payload)

        result = ml_service.predict_price_response(payload)

        confidence = float(result.get("confidence_score", 0))
        update_model_confidence(confidence)

        simulated_accuracy = random.choice([0.92, 0.89, 0.86, 0.82, 0.78])
        update_accuracy(simulated_accuracy)

        return jsonify(result), 200

    except Exception as e:
        API_ERRORS.labels(
            endpoint="/api/ml/predict-price",
            error_type=type(e).__name__
        ).inc()

        return jsonify({
            "error": str(e)
        }), 500

@ml_bp.route("/classification", methods=["POST"])
def classification():
    try:
        payload = request.get_json(force=True, silent=True) or {}
        result = ml_service.run_classification(payload)
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"detail": str(exc)}), 400
    except Exception as exc:
        return jsonify({"detail": str(exc)}), 500


@ml_bp.route("/clustering", methods=["POST"])
def clustering():
    try:
        payload = request.get_json(force=True, silent=True) or {}
        result = ml_service.run_clustering(payload)
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"detail": str(exc)}), 400
    except Exception as exc:
        return jsonify({"detail": str(exc)}), 500


@ml_bp.route("/forecast", methods=["POST"])
def forecast():
    try:
        body = request.get_json(silent=True) or {}
        steps = int(body.get("steps", request.args.get("steps", 6)))
        use_db = bool(body.get("use_db", False))

        if use_db:
            records = ml_service.get_timeseries_records_from_db()
        else:
            records = ml_service.to_records(body)

        if not records:
            return jsonify({"detail": "No data provided. Send data or set use_db=true."}), 400

        result = ml_service.run_timeseries(records, steps=steps)
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"detail": str(exc)}), 400
    except Exception as exc:
        return jsonify({"detail": str(exc)}), 500


@ml_bp.route("/anomaly-detection", methods=["POST"])
def anomaly_detection():
    try:
        body = request.get_json(silent=True) or {}
        records = ml_service.to_records(body)
        result = ml_service.run_anomaly_detection(records)
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"detail": str(exc)}), 400
    except Exception as exc:
        return jsonify({"detail": str(exc)}), 500


# Legacy compatibility endpoints from former ML backend
@legacy_ml_bp.route("/predict", methods=["POST"])
def legacy_predict():
    try:
        data = request.get_json(silent=True) or []
        model_type = request.args.get("model")
        steps = int(request.args.get("steps", 5))
        result = ml_service.run_legacy_predict(data, model_type=model_type, steps=steps)
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"error": str(exc)}), 400
    except Exception as exc:
        return jsonify({"error": str(exc)}), 500


@legacy_ml_bp.route("/api/timeseries/forecast", methods=["POST"])
def legacy_timeseries_forecast():
    return forecast()


@legacy_ml_bp.route("/api/anomalies/detect", methods=["POST"])
def legacy_anomalies_detect():
    return anomaly_detection()


@legacy_ml_bp.route("/health", methods=["GET"])
def health():
    return jsonify({"status": "ok", "service": "unified-backend-ml"}), 200
