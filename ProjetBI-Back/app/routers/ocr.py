from flask import Blueprint, request, jsonify
import os
import tempfile
import cv2
import easyocr
import re
from sqlalchemy import text
from app.database import data_engine
import datetime

ocr_bp = Blueprint("ocr", __name__, url_prefix="/api/ocr")

# Lazy load OCR reader to save memory until needed
reader = None

def get_reader():
    global reader
    if reader is None:
        reader = easyocr.Reader(['fr', 'en'])
    return reader

def parse_date(date_str):
    # Try to parse some common patterns found in the script
    if not date_str or date_str == "Non trouvée":
        return None
    try:
        # Simplistic parsing, can be improved. Example: 12/05/2026
        # Just returning it as a string might fail SQL Server datetime conversion if not standard.
        # Let's try to convert to YYYY-MM-DD
        import dateutil.parser
        dt = dateutil.parser.parse(date_str, fuzzy=True)
        return dt.strftime('%Y-%m-%dT00:00:00')
    except:
        return None

@ocr_bp.route("/extract-poster", methods=["POST"])
def extract_poster():
    if 'file' not in request.files:
        return jsonify({"detail": "No file part"}), 400
    
    file = request.files['file']
    if file.filename == '':
        return jsonify({"detail": "No selected file"}), 400
    
    # Save temp file
    fd, temp_path = tempfile.mkstemp(suffix=".png")
    os.close(fd)
    
    try:
        file.save(temp_path)
        
        # Load image
        image = cv2.imread(temp_path)
        if image is None:
            return jsonify({"detail": "Invalid image"}), 400
            
        # Preprocessing
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        gray = cv2.resize(gray, None, fx=2, fy=2)
        blur = cv2.bilateralFilter(gray, 11, 17, 17)
        processed = cv2.threshold(blur, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)[1]
        
        # OCR
        r = get_reader()
        results = r.readtext(processed, detail=0, paragraph=True)
        text_str = " ".join(results)
        
        # --- EXTRACTION LOGIC (from main.py) ---
        
        # DATE
        patterns = [
            r'\d{1,2}[/-]\d{1,2}[/-]\d{2,4}',
            r'\d{1,2}\s(?:janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)\s\d{2,4}',
            r'\d{1,2}\s(?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)\s\d{2,4}'
        ]
        dates = []
        for pattern in patterns:
            dates.extend(re.findall(pattern, text_str, flags=re.IGNORECASE))
        date_evenement = dates[0] if len(dates) > 0 else None
        
        # TYPE
        types = {
            "Concert": ["concert", "live", "music", "festival", "dj"],
            "Conférence": ["conférence", "conference", "seminar", "talk"],
            "Sport": ["match", "football", "sport", "tournament"],
            "Soirée": ["party", "soirée", "night"],
            "Exposition": ["expo", "exposition", "gallery"]
        }
        lower_text = text_str.lower()
        event_type = "Inconnu"
        for t, keywords in types.items():
            for keyword in keywords:
                if keyword in lower_text:
                    event_type = t
                    break
                    
        # NOM
        event_name = "Event OCR"
        sentences = text_str.split()
        if len(sentences) >= 6:
            event_name = " ".join(sentences[:6])
        elif len(sentences) > 0:
            event_name = " ".join(sentences)
            
        # PRIX et PERFORMER ignorés car non présents dans Dim_Event
        
        # Convert date
        parsed_date = parse_date(date_evenement)
        if not parsed_date:
            parsed_date = datetime.datetime.now().strftime('%Y-%m-%dT00:00:00')  # default
            
        # INSERT INTO Dim_Event
        with data_engine.begin() as conn:
            # We don't specify id_event because it's usually an identity column
            # Wait, let's check if it is identity. Our check earlier showed: ('id_event', 'int').
            ident_query = text(f"SELECT is_identity FROM sys.columns WHERE object_id = OBJECT_ID('Dim_Event') AND name = 'id_event'")
            ident_res = conn.execute(ident_query).fetchone()
            is_identity = ident_res[0] if ident_res else False
            
            if is_identity:
                insert_query = text("INSERT INTO Dim_Event (title, type, event_date) VALUES (:t, :ty, :d)")
                conn.execute(insert_query, {"t": event_name[:255], "ty": event_type[:255], "d": parsed_date})
            else:
                # Need to manually increment ID
                res_max = conn.execute(text("SELECT ISNULL(MAX(id_event), 0) FROM Dim_Event"))
                new_id = int(res_max.fetchone()[0]) + 1
                insert_query = text("INSERT INTO Dim_Event (id_event, title, type, event_date) VALUES (:id, :t, :ty, :d)")
                conn.execute(insert_query, {"id": new_id, "t": event_name[:255], "ty": event_type[:255], "d": parsed_date})
                
        return jsonify({
            "message": "Affiche analysée et événement inséré",
            "data": {
                "title": event_name,
                "type": event_type,
                "event_date": parsed_date
            }
        }), 200

    except Exception as e:
        error_msg = str(e)
        print(f"Error extracting poster: {error_msg}")
        
        # Si le fichier est corrompu, on supprime le cache pour forcer un re-téléchargement propre
        if "retrieval incomplete" in error_msg or "urlopen error" in error_msg or "not a zip file" in error_msg.lower():
            import shutil
            model_dir = os.path.expanduser('~/.EasyOCR/model')
            if os.path.exists(model_dir):
                shutil.rmtree(model_dir, ignore_errors=True)
            return jsonify({"detail": "Le modèle IA était corrompu (téléchargement interrompu). Le cache a été nettoyé. Veuillez cliquer à nouveau pour réessayer."}), 500
            
        return jsonify({"detail": error_msg}), 500
    finally:
        if os.path.exists(temp_path):
            os.remove(temp_path)
