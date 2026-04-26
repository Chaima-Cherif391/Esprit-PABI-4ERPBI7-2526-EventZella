from flask import Blueprint, request, jsonify
from app.database import engine
from sqlalchemy import text

chat_bp = Blueprint('chat', __name__)

@chat_bp.route('/api/chat', methods=['POST'])
def chat():
    data = request.json
    user_message = data.get('message', '').lower()
    
    response_text = "Désolé, je n'ai pas compris votre question sur les données Power BI."
    
    try:
        # Logique de détection de mots-clés pour requêtes SQL simples
        if "revenu" in user_message or "chiffre" in user_message:
            with engine.connect() as connection:
                # Exemple de requête (à adapter selon vos tables réelles)
                result = connection.execute(text("SELECT SUM(TotalAmount) FROM FactEvents")).fetchone()
                val = result[0] if result[0] else 0
                response_text = f"Le chiffre d'affaires total enregistré est de {val:,.2f} TND."
        
        elif "événement" in user_message or "nombre" in user_message:
            with engine.connect() as connection:
                result = connection.execute(text("SELECT COUNT(*) FROM FactEvents")).fetchone()
                count = result[0]
                response_text = f"Il y a actuellement {count} événements enregistrés."
                
        elif "utilisateur" in user_message:
            with engine.connect() as connection:
                result = connection.execute(text("SELECT COUNT(*) FROM Users")).fetchone()
                count = result[0]
                response_text = f"Votre application compte {count} utilisateurs inscrits."
        
        else:
            response_text = "Je suis connecté à votre base SQL Server ! Je peux vous donner des infos sur les revenus ou le nombre d'événements. Que voulez-vous savoir ?"

    except Exception as e:
        print(f"Chat error: {e}")
        response_text = "Je suis prêt à répondre, mais je n'ai pas pu accéder aux tables SQL."

    return jsonify({"answer": response_text})
