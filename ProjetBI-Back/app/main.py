from flask import Flask, jsonify
from flask_cors import CORS
from app.database import Base, engine
from app.core.config import CORS_ALLOWED_ORIGINS
from app.routers.auth import auth_api_bp, auth_legacy_bp
from app.routers.ml import ml_bp, legacy_ml_bp
from app.routers.chat import chat_bp
from app.routers.notifications import notif_bp
from app.routers.forecast import forecast_bp
from app.routers.pdf_export import pdf_bp

try:
    Base.metadata.create_all(bind=engine)
    print("✅ Tables créées / vérifiées")
except Exception as e:
    print(f"⚠️ Erreur DB : {e}")

app = Flask(__name__)

origins = [origin.strip() for origin in CORS_ALLOWED_ORIGINS.split(",") if origin.strip()]

CORS(
    app,
    resources={r"/*": {"origins": "*"}},
    supports_credentials=True,
    allow_headers=["*"],
    methods=["GET", "POST", "PUT", "DELETE", "OPTIONS"]
)

app.register_blueprint(auth_api_bp)
app.register_blueprint(auth_legacy_bp)
app.register_blueprint(ml_bp)
app.register_blueprint(legacy_ml_bp)
app.register_blueprint(chat_bp)
app.register_blueprint(notif_bp)
app.register_blueprint(forecast_bp)
app.register_blueprint(pdf_bp)

@app.route("/", methods=["GET"])
def root():
    return jsonify({"message": "Unified BI API", "status": "ok"})