FROM python:3.10-slim

WORKDIR /app

# Dépendances système utiles pour pandas, sklearn, pyodbc, etc.
RUN apt-get update && apt-get install -y \
    gcc \
    g++ \
    curl \
    unixodbc-dev \
    && rm -rf /var/lib/apt/lists/*

# Copier requirements
COPY requirements.txt .

# Installer les dépendances Python
RUN pip install --no-cache-dir -r requirements.txt

# Copier tout le projet backend
COPY . .

# Port utilisé par Flask
EXPOSE 5000

# Lancer l'API Flask
CMD ["python", "-m", "flask", "--app", "app.main", "run", "--host=0.0.0.0", "--port=5000"]