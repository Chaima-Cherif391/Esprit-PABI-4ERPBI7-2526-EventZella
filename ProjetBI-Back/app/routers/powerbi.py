from flask import Blueprint, jsonify, request
from app.database import engine
from sqlalchemy.orm import Session
from app.models.user import User
import jose.jwt as jwt

pbi_bp = Blueprint('powerbi', __name__, url_prefix='/api/powerbi')

# Configuration des rapports par rôle
# On ajoute filterPaneEnabled=false et navContentPaneEnabled=false pour masquer les volets
REPORT_CONFIG = {
    "CEO": {
        "reportId": "115669f4-efa1-420c-b652-23d2cfd43c77",
        "embedUrl": "https://app.powerbi.com/reportEmbed?reportId=115669f4-efa1-420c-b652-23d2cfd43c77&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&filterPaneEnabled=false&navContentPaneEnabled=false&pageName=df6243e1614b506d8b8f",
        "type": "pbi"
    },
    "MARKETING": {
        "reportId": "641a8002-de0a-4435-921b-9dffd88227aa",
        "embedUrl": "https://app.powerbi.com/reportEmbed?reportId=641a8002-de0a-4435-921b-9dffd88227aa&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&filterPaneEnabled=false&navContentPaneEnabled=false&pageName=df6243e1614b506d8b8f",
        "type": "pbi"
    },
    "ADMIN": {
        "reportId": "a1cafe20-9343-49e4-be1c-02c4eb5873f7",
        "embedUrl": "https://app.powerbi.com/reportEmbed?reportId=a1cafe20-9343-49e4-be1c-02c4eb5873f7&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&filterPaneEnabled=false&navContentPaneEnabled=false&pageName=df6243e1614b506d8b8f",
        "grafanaUrl": "http://localhost:3000/public-dashboards/0664ff1737b24dc7b8e45b2014c56628",
        "type": "mixed"
    }
}

@pbi_bp.route('/embed-info', methods=['GET'])
def get_embed_info():
    auth_header = request.headers.get('Authorization')
    if not auth_header or not auth_header.startswith('Bearer '):
        return jsonify({"error": "Unauthorized"}), 401
    
    token = auth_header.split(' ')[1]
    try:
        payload = jwt.get_unverified_claims(token)
        identifier = (payload.get('email') or 
                     payload.get('preferred_username') or 
                     payload.get('unique_name') or
                     payload.get('upn') or
                     payload.get('sub'))

        if not identifier:
            return jsonify({"error": "No identifier found in token", "available_keys": list(payload.keys())}), 400

        with Session(engine) as session:
            user = session.query(User).filter(
                (User.email == identifier) | (User.full_name == identifier) | (User.id == identifier)
            ).first()
            
            if not user:
                return jsonify({"error": f"User '{identifier}' not found in DB"}), 404
            
            user_role = user.role.upper() if user.role else "USER"
            
            if user_role not in REPORT_CONFIG:
                return jsonify({"error": f"No report for role: {user_role}"}), 403
            
            config = REPORT_CONFIG[user_role]
            return jsonify({
                "reportId": config.get("reportId"),
                "embedUrl": config.get("embedUrl"),
                "grafanaUrl": config.get("grafanaUrl"),
                "type": config.get("type"),
                "role": user_role
            })

    except Exception as e:
        return jsonify({"error": str(e)}), 500
