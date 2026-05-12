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
        "reportId": "f3885d55-5e21-4067-8f3c-d2ab70ff73ae",
        "embedUrl": "https://app.powerbi.com/reportEmbed?reportId=f3885d55-5e21-4067-8f3c-d2ab70ff73ae&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&filterPaneEnabled=false&navContentPaneEnabled=false&pageName=df6243e1614b506d8b8f&bookmarkGuid=59c679a7afb0fda4b7b0",
        "type": "pbi"
    },
    "MARKETING": {
        "reportId": "63031891-b46d-4e85-ab10-751af63f86bb",
        "embedUrl": "https://app.powerbi.com/reportEmbed?reportId=63031891-b46d-4e85-ab10-751af63f86bb&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&filterPaneEnabled=false&navContentPaneEnabled=false&pageName=df6243e1614b506d8b8f&bookmarkGuid=59c679a7afb0fda4b7b0",
        "type": "pbi"
    },
    "ADMIN": {
        "reportId": "e96971bb-7223-40d0-986a-0d764dc41d75",
        "embedUrl": "https://app.powerbi.com/reportEmbed?reportId=e96971bb-7223-40d0-986a-0d764dc41d75&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&filterPaneEnabled=false&navContentPaneEnabled=false&pageName=df6243e1614b506d8b8f&bookmarkGuid=59c679a7afb0fda4b7b0",
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
