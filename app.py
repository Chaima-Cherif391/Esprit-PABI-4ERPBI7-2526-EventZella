from flask import Flask, jsonify
import subprocess
import os

app = Flask(__name__)

@app.route('/run-talend', methods=['GET'])
def run_talend():
    try:
        # 1️⃣ Build avec Maven classique (FIX)
        subprocess.run(
            "mvnd clean package",
            cwd=r"C:\Program Files (x86)\TOS_DI-8.0.1\studio\workspace\TEST\poms",
            shell=True,
            check=True
        )

        # 2️⃣ Trouver automatiquement le .bat généré
        base_path = r"C:\Program Files (x86)\TOS_DI-8.0.1\studio\workspace\TEST"
        
        bat_file = None
        for root, dirs, files in os.walk(base_path):
            for file in files:
                if file.endswith(".bat"):
                    bat_file = os.path.join(root, file)
                    break

        if not bat_file:
            return {"status": "error", "message": "Aucun .bat trouvé"}

        # 3️⃣ Exécuter le job Talend
        result = subprocess.run(
            bat_file,
            shell=True,
            capture_output=True,
            text=True
        )

        return jsonify({
            "status": "success",
            "bat": bat_file,
            "output": result.stdout
        })

    except subprocess.CalledProcessError as e:
        return jsonify({
            "status": "error",
            "message": "Erreur Maven",
            "details": e.stderr
        })

    except Exception as e:
        return jsonify({
            "status": "error",
            "message": str(e)
        })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)