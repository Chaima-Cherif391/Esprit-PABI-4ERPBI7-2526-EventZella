from flask import Blueprint, request, jsonify
from app.services.ai_assistant_service import AIAssistantService

ai_assistant_bp = Blueprint('ai_assistant', __name__)
ai_service = AIAssistantService()

@ai_assistant_bp.route('/api/ai/chat', methods=['POST'])
def chat():
    data = request.json
    if not data or 'message' not in data:
        return jsonify({"error": "Message manquant"}), 400
    
    user_message = data['message']
    response_text = ai_service.chat(user_message)
    
    return jsonify({
        "answer": response_text,
        "sender": "AI Assistant"
    })
@ai_assistant_bp.route('/api/ai/strategic-report', methods=['GET'])
def strategic_report():
    report = ai_service.generate_strategic_report()
    return jsonify({
        "report": report
    })
