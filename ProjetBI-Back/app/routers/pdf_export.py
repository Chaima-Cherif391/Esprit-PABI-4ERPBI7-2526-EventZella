from flask import Blueprint, send_file, jsonify, request
import requests
import os
import base64

pdf_bp = Blueprint('pdf_export', __name__)

# Config
URL_TEST = "http://127.0.0.1:5678/webhook-test/generate-pdf"
URL_PROD = "http://127.0.0.1:5678/webhook/generate-pdf"
CACHE_PDF = "last_report.pdf"

PDFSHIFT_API_URL = "https://api.pdfshift.io/v3/convert/pdf"
PDFSHIFT_API_KEY = "sk_21df17b1910fc9af84bee9be8332123be784b304"

@pdf_bp.route('/api/pdf/download', methods=['GET'])
def download_latest_pdf():
    pdf_content = None
    
    # 1. Tentative via n8n (On augmente le timeout à 30 secondes car le PDF est lourd)
    for url in [URL_TEST, URL_PROD]:
        try:
            print(f"Tentative d'appel n8n sur: {url}")
            resp = requests.get(url, timeout=40) 
            if resp.status_code == 200:
                if 'application/pdf' in resp.headers.get('Content-Type', ''):
                    pdf_content = resp.content
                    print(f"✅ Succès n8n sur {url}")
                    break
                else:
                    print(f"⚠️ n8n a répondu mais pas avec un PDF (Type: {resp.headers.get('Content-Type')})")
        except Exception as e:
            print(f"❌ Echec sur {url}: {e}")
            continue

    # 2. SI n8n ECHOUE -> Appel DIRECT à PDFShift
    if not pdf_content:
        try:
            print("n8n non dispo ou trop lent, tentative directe via PDFShift...")
            target_url = "https://e7c4-196-229-172-155.ngrok-free.app" 
            
            auth_header = "Basic " + base64.b64encode(f"api:{PDFSHIFT_API_KEY}".encode()).decode()
            headers = {"Authorization": auth_header, "Content-Type": "application/json"}
            body = {"source": target_url, "landscape": True, "use_print_media": True}
            
            resp = requests.post(PDFSHIFT_API_URL, json=body, headers=headers, timeout=20)
            if resp.status_code == 200:
                pdf_content = resp.content
                print("✅ Succès PDFShift Direct")
        except Exception as e:
            print(f"❌ Erreur PDFShift direct: {e}")

    # 3. Sauvegarde et Envoi
    if pdf_content:
        with open(CACHE_PDF, 'wb') as f:
            f.write(pdf_content)
        return send_file(CACHE_PDF, mimetype='application/pdf', as_attachment=True, download_name='Rapport_BI.pdf')

    # 4. Cache
    if os.path.exists(CACHE_PDF):
        print("ℹ️ Envoi de la version de sauvegarde (Cache)")
        return send_file(CACHE_PDF, mimetype='application/pdf', as_attachment=True, download_name='Rapport_BI_Ancien.pdf')
    
    return jsonify({"error": "Erreur de génération du PDF. Vérifiez votre n8n."}), 500
