from flask import Blueprint, jsonify, request

from app.services.ml_service import MLService
from app.services.n8n_service import N8nService
from app.core.monitoring import (
    detect_missing_values,
    detect_drift,
    mark_new_data_received,
    update_model_confidence
)


ml_bp = Blueprint("ml", __name__, url_prefix="/api/ml")
legacy_ml_bp = Blueprint("ml_legacy", __name__)

ml_service = MLService()
n8n_service = N8nService()


@ml_bp.route("/predict-price", methods=["POST"])
def predict_price():
    try:
        payload = request.get_json(force=True, silent=True) or {}
        
        # Monitoring
        mark_new_data_received()
        detect_missing_values(payload)
        detect_drift(payload)
        
        result = ml_service.predict_price_response(payload)
        
        # Si le modèle retourne un score de confiance, on le logue
        if "confidence" in result:
            update_model_confidence(result["confidence"])
            
        n8n_service.emit_prediction_event({"event": "prediction.generated", "payload": payload, "result": result})
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"detail": str(exc)}), 400
    except Exception as exc:
        return jsonify({"detail": str(exc)}), 500


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


@ml_bp.route("/anomaly-db", methods=["GET"])
def anomaly_db():
    try:
        records = ml_service.get_anomaly_records_from_db()
        if not records:
            return jsonify({"detail": "No records found in database."}), 404
        result = ml_service.run_anomaly_detection(records)
        return jsonify(result), 200
    except ValueError as exc:
        return jsonify({"detail": str(exc)}), 400
    except Exception as exc:
        return jsonify({"detail": str(exc)}), 500


@ml_bp.route("/anomaly-realtime", methods=["POST"])
def anomaly_realtime():
    try:
        payload = request.get_json(force=True, silent=True) or {}
        result = ml_service.predict_anomaly_realtime(payload)
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
