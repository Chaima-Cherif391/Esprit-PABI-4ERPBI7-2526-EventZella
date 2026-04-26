from flask import Blueprint, jsonify, request
from app.database import SessionLocal
from app.models.notification import Notification
from sqlalchemy import func

notif_bp = Blueprint('notifications', __name__)

@notif_bp.route('/api/notifications/unread-count', methods=['GET'])
def get_unread_count():
    db = SessionLocal()
    try:
        count = db.query(func.count(Notification.id)).filter(Notification.is_read == False).scalar()
        return jsonify({"count": count})
    finally:
        db.close()

@notif_bp.route('/api/notifications/mark-read', methods=['POST'])
def mark_as_read():
    db = SessionLocal()
    try:
        db.query(Notification).filter(Notification.is_read == False).update({"is_read": True})
        db.commit()
        return jsonify({"message": "All marked as read"})
    finally:
        db.close()

@notif_bp.route('/api/notifications/trigger', methods=['POST'])
def trigger_notification():
    data = request.json
    db = SessionLocal()
    try:
        new_notif = Notification(
            title=data.get('title', 'Nouveauté n8n'),
            message=data.get('message', 'Un email a été traité.'),
            data=data.get('data') # On stocke le rapport ici
        )
        db.add(new_notif)
        db.commit()
        return jsonify({"message": "Notification triggered", "id": new_notif.id})
    finally:
        db.close()

@notif_bp.route('/api/notifications/latest', methods=['GET'])
def get_latest_notifications():
    db = SessionLocal()
    try:
        notifs = db.query(Notification).order_by(Notification.created_at.desc()).limit(10).all()
        return jsonify([{
            "id": n.id,
            "title": n.title,
            "message": n.message,
            "data": n.data,
            "is_read": n.is_read,
            "created_at": n.created_at.isoformat()
        } for n in notifs])
    finally:
        db.close()
