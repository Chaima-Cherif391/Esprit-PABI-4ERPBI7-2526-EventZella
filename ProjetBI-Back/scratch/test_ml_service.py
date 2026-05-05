import sys
import os

# Add the project root to sys.path
sys.path.append(os.path.abspath("."))

# Mock environment variables needed for config if they aren't set
os.environ["ML_MODELS_DIR"] = "ml_models"
os.environ["DB_SERVER"] = "localhost"
os.environ["DB_NAME"] = "test"
os.environ["DB_USER"] = "test"
os.environ["DB_PASSWORD"] = "test"
os.environ["DB_DRIVER"] = "ODBC Driver 17 for SQL Server"

from app.services.ml_service import MLService

service = MLService()
print(f"Anomaly Model Loaded: {service.anomaly_model is not None}")
print(f"Scaler Model Loaded: {service.scaler_model is not None}")
if service.anomaly_model:
    print(f"Anomaly Model Type: {type(service.anomaly_model)}")
