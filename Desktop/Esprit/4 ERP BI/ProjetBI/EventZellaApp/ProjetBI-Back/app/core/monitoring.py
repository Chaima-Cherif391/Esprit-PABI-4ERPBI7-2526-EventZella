import logging
from prometheus_client import Counter, Histogram, Gauge, generate_latest, CONTENT_TYPE_LATEST
from datetime import datetime, timezone

# ==========================
# LOGGING
# ==========================

logging.basicConfig(
    filename="monitoring.log",
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

# ==========================
# API METRICS
# ==========================

REQUEST_COUNT = Counter(
    "api_requests_total",
    "Total number of API requests",
    ["method", "endpoint", "status"]
)

REQUEST_LATENCY = Histogram(
    "api_request_latency_seconds",
    "API request latency in seconds",
    ["endpoint"]
)

API_ERRORS = Counter(
    "api_errors_total",
    "Total number of API errors",
    ["endpoint", "error_type"]
)

# ==========================
# MODEL METRICS
# ==========================

MODEL_CONFIDENCE = Gauge(
    "model_confidence_score",
    "Latest model confidence score"
)

MODEL_CONFIDENCE_AVG = Gauge(
    "model_confidence_average",
    "Average model confidence score"
)

MODEL_ACCURACY_CURRENT = Gauge(
    "model_accuracy_current",
    "Current model accuracy"
)

MODEL_ACCURACY_BASELINE = Gauge(
    "model_accuracy_baseline",
    "Baseline model accuracy"
)

ACCURACY_DEGRADATION = Gauge(
    "accuracy_degradation_detected",
    "1 if accuracy degradation detected, else 0"
)

# ==========================
# DATA METRICS
# ==========================

MISSING_VALUES_TOTAL = Gauge(
    "data_missing_values_total",
    "Number of missing values in prediction input"
)

DATA_DRIFT_DETECTED = Gauge(
    "data_drift_detected",
    "1 if data drift detected, else 0"
)

DATA_FRESHNESS_SECONDS = Gauge(
    "data_freshness_seconds",
    "Data freshness in seconds"
)

# ==========================
# BASELINES
# ==========================
#normalement le modèle est à 90% de précision=>niveau de référence.
BASELINE_ACCURACY = 0.90
BASELINE_CONFIDENCE = 0.70
BASELINE_LATENCY_SECONDS = 1.0

MODEL_ACCURACY_BASELINE.set(BASELINE_ACCURACY)

confidence_history = []

last_data_time = datetime.now(timezone.utc)


def mark_new_data_received():
    global last_data_time
    last_data_time = datetime.now(timezone.utc)
    DATA_FRESHNESS_SECONDS.set(0)


def update_data_freshness():
    now = datetime.now(timezone.utc)
    freshness = (now - last_data_time).total_seconds()
    DATA_FRESHNESS_SECONDS.set(freshness)

    if freshness > 300:
        logging.warning(
            f"Data freshness issue detected. Last data received {freshness} seconds ago."
        )
def detect_missing_values(payload: dict) -> int:
    missing_count = 0

    expected_fields = [
        "market_count",
        "nbr_visitors",
        "nbr_reservations",
        "marketing_spend",
        "rating",
        "trend_score",
        "growth_rate_pct",
        "capacity_min",
        "capacity_max",
        "season_encoded",
        "event_type_encoded",
        "venue_type_encoded",
        "city_encoded",
    ]

    for field in expected_fields:
        if field not in payload or payload.get(field) is None or payload.get(field) == "":
            missing_count += 1

    MISSING_VALUES_TOTAL.set(missing_count)

    if missing_count > 0:
        logging.warning(f"Missing values detected: {missing_count}")

    return missing_count


def detect_drift(payload: dict) -> int:
    drift = 0
    reasons = []

    market_count = float(payload.get("market_count", 0) or 0)
    nbr_visitors = float(payload.get("nbr_visitors", 0) or 0)
    marketing_spend = float(payload.get("marketing_spend", 0) or 0)
    rating = float(payload.get("rating", 0) or 0)
    growth_rate_pct = float(payload.get("growth_rate_pct", 0) or 0)

    if market_count > 100:
        drift = 1
        reasons.append("market_count too high")

    if nbr_visitors > 50000:
        drift = 1
        reasons.append("nbr_visitors too high")

    if marketing_spend > 100000:
        drift = 1
        reasons.append("marketing_spend too high")

    if rating < 1 or rating > 5:
        drift = 1
        reasons.append("rating out of range")

    if growth_rate_pct < -50 or growth_rate_pct > 100:
        drift = 1
        reasons.append("growth_rate_pct abnormal")

    DATA_DRIFT_DETECTED.set(drift)

    if drift == 1:
        logging.warning(f"Data drift detected. Reasons: {reasons}")
        logging.warning("Retraining trigger recommended because drift was detected.")

    return drift


def update_model_confidence(confidence_score: float):
    MODEL_CONFIDENCE.set(confidence_score)

    confidence_history.append(confidence_score)

    if len(confidence_history) > 100:
        confidence_history.pop(0)

    avg_confidence = sum(confidence_history) / len(confidence_history)
    MODEL_CONFIDENCE_AVG.set(avg_confidence)

    if avg_confidence < BASELINE_CONFIDENCE:
        logging.warning(
            f"Confidence decrease detected. Current average={avg_confidence}, baseline={BASELINE_CONFIDENCE}"
        )


def update_accuracy(current_accuracy: float):
    MODEL_ACCURACY_CURRENT.set(current_accuracy)
    #si ton modèle devient 5% moins bon que la normale, alors problème
    if current_accuracy < BASELINE_ACCURACY - 0.05:
        ACCURACY_DEGRADATION.set(1)
        logging.warning(
            f"Accuracy degradation detected. Current={current_accuracy}, baseline={BASELINE_ACCURACY}"
        )
        logging.warning("Retraining trigger recommended because accuracy dropped.")
    else:
        ACCURACY_DEGRADATION.set(0)


def metrics_response():
    return generate_latest(), 200, {"Content-Type": CONTENT_TYPE_LATEST}