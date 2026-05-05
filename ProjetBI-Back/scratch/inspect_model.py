import joblib
from pathlib import Path

model_path = Path(r"c:\docker-projects\finaaalll\ProjetBI-Back\ml_models\anomaly_model.pkl")
if model_path.exists():
    model = joblib.load(model_path)
    print(f"Model type: {type(model)}")
    if isinstance(model, dict):
        print(f"Keys: {list(model.keys())}")
        if 'features' in model:
            print(f"Features: {model['features']}")
        for k, v in model.items():
            print(f"Key '{k}' type: {type(v)}")
    if hasattr(model, "feature_names_in_"):
        print(f"Feature names: {model.feature_names_in_}")
    else:
        print("No feature names found in model root.")
else:
    print("File not found")
