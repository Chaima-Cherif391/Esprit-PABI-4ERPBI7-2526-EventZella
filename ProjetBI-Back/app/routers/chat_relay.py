from flask import Blueprint, request, jsonify

chat_relay_bp = Blueprint('chat_relay', __name__)

# On garde les messages en mémoire vive (ils s'effacent au redémarrage du serveur)
messages_queue = []

@chat_relay_bp.route('/api/chat/send', methods=['POST'])
def send_message():
    data = request.json
    if not data or 'text' not in data or 'sender' not in data:
        return jsonify({"error": "Données invalides"}), 400
    
    new_msg = {
        "id": len(messages_queue) + 1,
        "sender": data['sender'], # 'CEO' ou 'MARKETING'
        "text": data['text'],
        "timestamp": data.get('timestamp', '')
    }
    messages_queue.append(new_msg)
    
    # On garde seulement les 50 derniers messages pour ne pas saturer la RAM
    if len(messages_queue) > 50:
        messages_queue.pop(0)
        
    return jsonify({"status": "success", "message": new_msg})

@chat_relay_bp.route('/api/chat/sync', methods=['GET'])
def sync_messages():
    # Renvoie tous les messages actuels
    return jsonify(messages_queue)
