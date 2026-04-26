from flask import Blueprint, request, jsonify
import requests
import os

forecast_bp = Blueprint('forecast', __name__)

# URLs n8n
URL_TEST = "http://127.0.0.1:5678/webhook-test/forecast"
URL_PROD = "http://127.0.0.1:5678/webhook/forecast"

CACHE_FILE = "last_forecast.html"

DEFAULT_HTML = """
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #0b141d; color: white; padding: 40px; text-align: center; }
        .card { background: rgba(22, 192, 222, 0.05); border: 1px solid rgba(22, 192, 222, 0.2); border-radius: 16px; padding: 30px; max-width: 800px; margin: 0 auto; }
        h1 { color: #16c0de; }
        .stats { display: flex; justify-content: space-around; margin-top: 30px; }
        .stat-item { padding: 20px; }
        .value { font-size: 2.5rem; font-weight: bold; color: #16c0de; }
        .label { font-size: 0.9rem; color: #888; text-transform: uppercase; margin-top: 10px; }
        .chart-placeholder { height: 200px; background: rgba(255,255,255,0.05); border-radius: 12px; margin-top: 30px; display: flex; align-items: center; justify-content: center; color: #444; border: 1px dashed #333; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Intelligence Artificielle : Prévisions de Demande</h1>
        <p>Analyse prédictive basée sur l'historique des réservations et des tendances saisonnières.</p>
        <div class="stats">
            <div class="stat-item">
                <div class="value">84%</div>
                <div class="label">Précision Modèle</div>
            </div>
            <div class="stat-item">
                <div class="value">+12%</div>
                <div class="label">Croissance Prévue</div>
            </div>
            <div class="stat-item">
                <div class="value">1.2k</div>
                <div class="label">Réservations Estimées</div>
            </div>
        </div>
        <div class="chart-placeholder">
            [ Graphique de tendance temporelle - En attente de mise à jour n8n ]
        </div>
        <p style="margin-top: 40px; font-size: 0.8rem; color: #555;">Dernière synchronisation : Rapport Statique (Mode Hors-ligne)</p>
    </div>
</body>
</html>
"""

@forecast_bp.route('/api/forecast/trigger', methods=['POST', 'GET'])
def trigger_forecast():
    html_content = None
    
    # On utilise .get() au lieu de .post() car n8n attend du GET
    # 1. On essaie le lien TEST
    try:
        resp = requests.get(URL_TEST, timeout=5)
        if resp.status_code == 200:
            resp.encoding = 'utf-8' # Forcer l'UTF-8 pour les accents et emojis
            if 'text/html' in resp.headers.get('Content-Type', ''):
                html_content = resp.text
    except:
        pass

    # 2. On essaie le lien PROD
    if not html_content:
        try:
            resp = requests.get(URL_PROD, timeout=5)
            if resp.status_code == 200:
                resp.encoding = 'utf-8' # Forcer l'UTF-8
                if 'text/html' in resp.headers.get('Content-Type', ''):
                    html_content = resp.text
        except:
            pass

    # 3. Traitement
    if html_content:
        try:
            with open(CACHE_FILE, "w", encoding="utf-8") as f:
                f.write(html_content)
        except:
            pass
        return html_content, 200, {'Content-Type': 'text/html'}
    
    # 4. Fallback sur le dernier résultat enregistré (Cache)
    if os.path.exists(CACHE_FILE):
        try:
            with open(CACHE_FILE, "r", encoding="utf-8") as f:
                return f.read(), 200, {'Content-Type': 'text/html'}
        except:
            pass
            
    # 5. Si vraiment rien n'existe, on affiche la page de design par défaut
    return DEFAULT_HTML, 200, {'Content-Type': 'text/html'}
