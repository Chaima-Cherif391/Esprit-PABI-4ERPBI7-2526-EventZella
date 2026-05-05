import joblib
from pathlib import Path
import os

model_path = Path("ml_models/scaler.pkl")
if model_path.exists():
    scaler = joblib.load(model_path)
    print(f"Scaler type: {type(scaler)}")
    if hasattr(scaler, "feature_names_in_"):
        print(f"Features: {scaler.feature_names_in_}")
    else:
        print("Feature names not found in scaler.")
else:
    print("Scaler not found.")
