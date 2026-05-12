from flask import Blueprint, request, jsonify
import smtplib
import ssl
import os
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from app.database import SessionLocal
from app.models.user import User
from app.schemas.user import UserCreate, UserLogin, UserOut
from app.core.security import hash_password, verify_password, create_access_token, decode_token
from pydantic import ValidationError

VALID_ROLES = ["CEO", "MARKETING", "ADMIN"]

def send_recovery_email(receiver_email, temp_password):
    smtp_server = "smtp.gmail.com"
    port = 587  # For STARTTLS
    sender_email = os.getenv("SMTP_USER")
    password = os.getenv("SMTP_PASSWORD")

    print(f"DEBUG: Attempting to send recovery email to {receiver_email} using {sender_email}")

    if not sender_email or not password:
        print("DEBUG: SMTP credentials missing in .env")
        return False

    message = MIMEMultipart("alternative")
    message["Subject"] = "Eventzilla - Password Recovery"
    message["From"] = sender_email
    message["To"] = receiver_email

    text = f"Your temporary password is: {temp_password}\nPlease log in and change it immediately."
    html = f"""
    <html>
    <body style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #050c16; color: #fff; padding: 20px;">
        <div style="max-width: 600px; margin: auto; background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1); border-radius: 15px; padding: 30px; backdrop-filter: blur(10px);">
            <h2 style="color: #16c0de;">Password Recovery</h2>
            <p>Hello,</p>
            <p>You requested to reset your password for your <strong>Eventzilla</strong> account.</p>
            <div style="background: rgba(22, 192, 222, 0.1); border: 1px dashed #16c0de; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; color: #16c0de; margin: 20px 0; border-radius: 8px;">
                {temp_password}
            </div>
            <p>Please use this temporary password to log in and change it immediately from your profile settings.</p>
            <p>If you didn't request this, you can safely ignore this email.</p>
            <br>
            <p style="font-size: 12px; color: #888;">&copy; 2024 Eventzilla AI Dashboard. All rights reserved.</p>
        </div>
    </body>
    </html>
    """
    part1 = MIMEText(text, "plain")
    part2 = MIMEText(html, "html")
    message.attach(part1)
    message.attach(part2)

    try:
        server = smtplib.SMTP(smtp_server, port)
        server.starttls()
        server.login(sender_email, password)
        server.sendmail(sender_email, receiver_email, message.as_string())
        server.quit()
        print("DEBUG: Email sent successfully!")
        return True
    except Exception as e:
        print(f"DEBUG: Critical SMTP Error: {e}")
        return False


def _get_token_from_header():
    auth_header = request.headers.get("Authorization")
    if not auth_header or not auth_header.startswith("Bearer "):
        return None
    return auth_header.split(" ")[1]


def create_auth_blueprint(name: str, url_prefix: str) -> Blueprint:
    bp = Blueprint(name, __name__, url_prefix=url_prefix)

    @bp.route("/register", methods=["POST"])
    def register():
        db = SessionLocal()
        try:
            try:
                payload = UserCreate(**request.get_json(force=True, silent=True) or {})
            except ValidationError as exc:
                return jsonify({"detail": exc.errors()}), 422
            except Exception:
                return jsonify({"detail": "Invalid JSON"}), 400

            if payload.role not in VALID_ROLES:
                return jsonify({"detail": f"Invalid role. Use one of {VALID_ROLES}"}), 400

            existing = db.query(User).filter(User.email == payload.email).first()
            if existing:
                return jsonify({"detail": "Email already used"}), 400

            try:
                password_hash = hash_password(payload.password)
            except ValueError as exc:
                return jsonify({"detail": str(exc)}), 400
            except RuntimeError:
                return jsonify({"detail": "Password hashing failed"}), 500

            user = User(
                full_name=payload.full_name,
                email=payload.email,
                password=password_hash,
                role=payload.role,
            )
            db.add(user)
            db.commit()
            db.refresh(user)

            return jsonify(UserOut.model_validate(user).model_dump(mode="json")), 201
        finally:
            db.close()

    @bp.route("/login", methods=["POST"])
    def login():
        db = SessionLocal()
        try:
            data = request.get_json(force=True, silent=True) or {}
            try:
                payload = UserLogin(**data)
            except ValidationError as exc:
                return jsonify({"detail": exc.errors()}), 422
            except Exception:
                return jsonify({"detail": "Invalid JSON"}), 400

            user = db.query(User).filter(User.email == payload.email).first()
            if not user or not verify_password(payload.password, user.password):
                return jsonify({"detail": "Invalid email or password"}), 401

            if not user.is_active:
                return jsonify({"detail": "Account disabled"}), 403

            token = create_access_token({"sub": str(user.id), "role": user.role})
            
            # Simulation de la génération d'un token Power BI spécifique au rôle
            # Dans une version réelle, on appellerait l'API Power BI ici.
            pbi_token = f"MOCK_PBI_TOKEN_{user.role}_{user.id}"
            
            return jsonify(
                {
                    "access_token": token,
                    "powerbi_token": pbi_token,
                    "token_type": "bearer",
                    "user": UserOut.model_validate(user).model_dump(mode="json"),
                }
            ), 200
        finally:
            db.close()

    @bp.route("/me", methods=["GET"])
    def get_me():
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            try:
                payload = decode_token(token)
                user_id = int(payload.get("sub"))
            except Exception:
                return jsonify({"detail": "Invalid token"}), 401

            user = db.query(User).filter(User.id == user_id).first()
            if not user:
                return jsonify({"detail": "User not found"}), 404

            return jsonify(UserOut.model_validate(user).model_dump(mode="json")), 200
        finally:
            db.close()

    @bp.route("/ceo-only", methods=["GET"])
    def ceo_dashboard():
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            try:
                payload = decode_token(token)
                user_id = int(payload.get("sub"))
            except Exception:
                return jsonify({"detail": "Invalid token"}), 401

            user = db.query(User).filter(User.id == user_id).first()
            if not user or user.role != "CEO":
                return jsonify({"detail": "CEO only access"}), 403

            return jsonify({"message": f"Welcome CEO {user.full_name}"}), 200
        finally:
            db.close()

    @bp.route("/marketing-only", methods=["GET"])
    def marketing_dashboard():
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            try:
                payload = decode_token(token)
                user_id = int(payload.get("sub"))
            except Exception:
                return jsonify({"detail": "Invalid token"}), 401

            user = db.query(User).filter(User.id == user_id).first()
            if not user or user.role != "MARKETING":
                return jsonify({"detail": "Marketing only access"}), 403

            return jsonify({"message": f"Welcome {user.full_name}"}), 200
        finally:
            db.close()

    @bp.route("/users/marketing", methods=["GET"])
    def get_marketing_users():
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            users = db.query(User).filter(User.role == "MARKETING").all()
            return jsonify([UserOut.model_validate(u).model_dump(mode="json") for u in users]), 200
        finally:
            db.close()

    @bp.route("/users/all", methods=["GET"])
    def get_all_users():
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            # Récupérer tous les users SAUF les ADMIN
            users = db.query(User).filter(User.role != "ADMIN").all()
            return jsonify([UserOut.model_validate(u).model_dump(mode="json") for u in users]), 200
        finally:
            db.close()

    @bp.route("/users/<int:user_id>", methods=["PUT"])
    def update_user(user_id):
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            user = db.query(User).filter(User.id == user_id).first()
            if not user:
                return jsonify({"detail": "User not found"}), 404

            data = request.get_json(force=True, silent=True) or {}
            if "full_name" in data:
                user.full_name = data["full_name"]
            if "email" in data:
                user.email = data["email"]
            if "is_active" in data:
                user.is_active = data["is_active"]
            if "password" in data and data["password"]:
                user.password = hash_password(data["password"])
            if "role" in data and data["role"] in VALID_ROLES:
                user.role = data["role"]

            db.commit()
            db.refresh(user)
            return jsonify(UserOut.model_validate(user).model_dump(mode="json")), 200
        finally:
            db.close()

    @bp.route("/profile", methods=["PUT"])
    def update_profile():
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            try:
                payload = decode_token(token)
                user_id = int(payload.get("sub"))
            except Exception:
                return jsonify({"detail": "Invalid token"}), 401

            user = db.query(User).filter(User.id == user_id).first()
            if not user:
                return jsonify({"detail": "User not found"}), 404

            data = request.get_json(force=True, silent=True) or {}
            if "full_name" in data:
                user.full_name = data["full_name"]
            if "email" in data:
                # Optional: check if email is already taken by another user
                user.email = data["email"]
            if "password" in data and data["password"]:
                user.password = hash_password(data["password"])

            db.commit()
            db.refresh(user)
            return jsonify({
                "message": "Profile updated successfully",
                "user": UserOut.model_validate(user).model_dump(mode="json")
            }), 200
        finally:
            db.close()

    @bp.route("/users/<int:user_id>", methods=["DELETE"])
    def delete_user(user_id):
        token = _get_token_from_header()
        if not token:
            return jsonify({"detail": "Not authenticated"}), 401

        db = SessionLocal()
        try:
            user = db.query(User).filter(User.id == user_id).first()
            if not user:
                return jsonify({"detail": "User not found"}), 404

            db.delete(user)
            db.commit()
            return jsonify({"detail": "User deleted"}), 200
        finally:
            db.close()

    @bp.route("/forgot-password", methods=["POST"])
    def forgot_password():
        data = request.get_json(force=True, silent=True) or {}
        email = data.get("email")
        if not email:
            return jsonify({"detail": "Email is required"}), 400
        
        db = SessionLocal()
        try:
            user = db.query(User).filter(User.email == email).first()
            if user:
                # 1. Générer un mot de passe temporaire
                import random
                import string
                temp_pass = ''.join(random.choices(string.ascii_letters + string.digits, k=8))
                
                # 2. Mettre à jour en base de données
                user.password = hash_password(temp_pass)
                db.commit()
                
                # 3. Envoyer l'e-mail
                success = send_recovery_email(user.email, temp_pass)
                if not success:
                    return jsonify({"detail": "L'email n'a pas pu être envoyé. Vérifiez votre configuration SMTP (Mot de passe d'application)."}), 500
            
            return jsonify({"message": "Si un compte est associé à cet e-mail, vous recevrez des instructions sous peu."}), 200
        finally:
            db.close()

    return bp


auth_api_bp = create_auth_blueprint("auth_api", "/api/auth")
auth_legacy_bp = create_auth_blueprint("auth_legacy", "/auth")