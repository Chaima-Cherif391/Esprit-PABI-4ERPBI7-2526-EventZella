from flask import Blueprint, request, jsonify
from sqlalchemy.orm import Session
from sqlalchemy import text
from app.database import SessionLocal, engine, data_engine
from app.routers.auth import _get_token_from_header
from app.core.security import decode_token
from app.models.user import User
import pandas as pd
import io
import requests
from requests.auth import HTTPBasicAuth

admin_bp = Blueprint("admin", __name__, url_prefix="/api/admin")

def _check_admin():
    token = _get_token_from_header()
    if not token:
        return None, ("Not authenticated", 401)
    
    db = SessionLocal()
    try:
        try:
            payload = decode_token(token)
            user_id = int(payload.get("sub"))
        except Exception:
            return None, ("Invalid token", 401)
        
        user = db.query(User).filter(User.id == user_id).first()
        if not user or user.role != "ADMIN":
            return None, ("Admin only access", 403)
        
        return user, None
    finally:
        db.close()

@admin_bp.route("/tables", methods=["GET"])
def list_tables():
    user, error = _check_admin()
    if error:
        return jsonify({"detail": error[0]}), error[1]
    try:
        with data_engine.connect() as conn:
            # Liste les tables Dim et FACT (en ignorant les tables système)
            res = conn.execute(text("SELECT name FROM sys.tables WHERE name LIKE 'Dim%' OR name LIKE 'FACT%' ORDER BY name"))
            tables = [r[0] for r in res]
            return jsonify(tables), 200
    except Exception as e:
        return jsonify({"detail": str(e)}), 500

@admin_bp.route("/upload-table/<string:table_name>", methods=["POST"])
def upload_table_data(table_name):
    user, error = _check_admin()
    if error:
        return jsonify({"detail": error[0]}), error[1]
    
    if 'file' not in request.files:
        return jsonify({"detail": "No file part"}), 400
    
    file = request.files['file']
    if file.filename == '':
        return jsonify({"detail": "No selected file"}), 400
    
    try:
        # Read CSV
        content = file.read()
        df = pd.read_csv(io.BytesIO(content))
        
        with data_engine.begin() as conn:
            # 1. Récupérer les colonnes de la table cible
            col_query = text(f"SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = :t")
            table_cols_res = conn.execute(col_query, {"t": table_name}).fetchall()
            table_cols = {r[0]: r[1] for r in table_cols_res}
            
            if not table_cols:
                return jsonify({"detail": f"Table {table_name} not found"}), 404
            
            # 2. Vérifier s'il y a une colonne identity (auto-increment)
            ident_query = text(f"SELECT name FROM sys.columns WHERE object_id = OBJECT_ID(:t) AND is_identity = 1")
            identity_col_res = conn.execute(ident_query, {"t": table_name}).fetchone()
            identity_col = identity_col_res[0] if identity_col_res else None
            
            # 3. Filtrer les colonnes du CSV qui existent dans la table
            # On ignore la colonne identity si elle est dans le CSV car elle est gérée par la DB
            df_cols = [c for c in df.columns if c in table_cols and c != identity_col]
            
            # 4. Gérer l'ID manuel si pas d'identity et pas d'ID dans le CSV
            # On suppose que la 1ère colonne de la table est souvent la PK
            first_col = list(table_cols.keys())[0]
            if not identity_col and first_col not in df.columns and "int" in table_cols[first_col].lower():
                res_max = conn.execute(text(f"SELECT ISNULL(MAX([{first_col}]), 0) FROM [{table_name}]"))
                current_max = res_max.fetchone()[0]
                df[first_col] = range(int(current_max) + 1, int(current_max) + 1 + len(df))
                df_cols.append(first_col)

            # 5. Insertion ligne par ligne
            for _, row in df.iterrows():
                cols_str = ", ".join([f"[{c}]" for c in df_cols])
                params_str = ", ".join([f":{c}" for c in df_cols])
                
                # Conversion des types de base (dates notamment)
                row_data = {}
                for c in df_cols:
                    val = row[c]
                    if "datetime" in table_cols[c].lower() or "date" in table_cols[c].lower():
                        val = pd.to_datetime(val)
                    row_data[c] = val

                query = text(f"INSERT INTO [{table_name}] ({cols_str}) VALUES ({params_str})")
                conn.execute(query, row_data)
        
        return jsonify({"message": f"Successfully imported {len(df)} rows into {table_name}."}), 200
        
    except Exception as e:
        print(f"Upload error: {e}")
        return jsonify({"detail": str(e)}), 500

@admin_bp.route("/airflow/status", methods=["GET"])
def get_airflow_status():
    user, error = _check_admin()
    if error:
        return jsonify({"detail": error[0]}), error[1]
    
    try:
        # Airflow configuration
        # Since the backend is in Docker and Airflow is on the host (port 8081),
        # we use host.docker.internal to reach the host's port.
        url = "http://host.docker.internal:8081/api/v1/dags/talend_dwh_pipeline/dagRuns?limit=1&order_by=-execution_date"
        response = requests.get(url, auth=HTTPBasicAuth("airflow", "airflow"))
        
        if response.status_code == 200:
            data = response.json()
            runs = data.get("dag_runs", [])
            if runs:
                last_run = runs[0]
                return jsonify({
                    "status": last_run.get("state").upper(),
                    "last_execution": last_run.get("execution_date")
                }), 200
            else:
                return jsonify({"status": "NO RUNS", "last_execution": "N/A"}), 200
        else:
            return jsonify({"detail": f"Failed to connect to Airflow: {response.text}"}), response.status_code
    except Exception as e:
        return jsonify({"detail": str(e)}), 500

@admin_bp.route("/airflow/run", methods=["POST"])
def trigger_airflow_pipeline():
    user, error = _check_admin()
    if error:
        return jsonify({"detail": error[0]}), error[1]
    
    try:
        url = "http://host.docker.internal:8081/api/v1/dags/talend_dwh_pipeline/dagRuns"
        # Body can be empty, but must be valid JSON
        response = requests.post(url, json={}, auth=HTTPBasicAuth("airflow", "airflow"))
        
        if response.status_code in [200, 201]:
            return jsonify({"message": "Pipeline triggered successfully"}), 200
        else:
            return jsonify({"detail": f"Airflow error: {response.text}"}), response.status_code
    except Exception as e:
        return jsonify({"detail": str(e)}), 500
