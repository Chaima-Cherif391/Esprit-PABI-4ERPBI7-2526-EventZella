from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
import os
import pyodbc
import urllib.parse

drivers = pyodbc.drivers()
if "ODBC Driver 18 for SQL Server" in drivers:
    driver_name = "ODBC Driver 18 for SQL Server"
elif "ODBC Driver 17 for SQL Server" in drivers:
    driver_name = "ODBC Driver 17 for SQL Server"
else:
    driver_name = "SQL Server"

in_docker = os.path.exists('/.dockerenv')

if in_docker:
    server = os.getenv("DB_SERVER", "sqlserver,1433")
    connection_string = (
        f"DRIVER={{{driver_name}}};"
        f"SERVER={server};"
        "DATABASE=event_DWH;"
        "UID=sa;"
        "PWD=EventZella2024!;"
        "TrustServerCertificate=yes;"
    )
else:
    # Mode local demandé : Authentification Windows
    server = "."
    connection_string = (
        f"DRIVER={{{driver_name}}};"
        f"SERVER={server};"
        "DATABASE=event_DWH;"
        "Trusted_Connection=yes;"
        "TrustServerCertificate=yes;"
    )

params = urllib.parse.quote_plus(connection_string)
DATABASE_URL = f"mssql+pyodbc:///?odbc_connect={params}"

print(f">>> Connexion via odbc_connect directe avec le pilote : {driver_name} sur le serveur : {server}")

engine = create_engine(DATABASE_URL, pool_pre_ping=True)

# ENGINE ANALYTIQUE : Pour les tables FACT/DIM sur votre machine locale
# Si on est dans Docker, on utilise host.docker.internal pour sortir du conteneur
data_server = "host.docker.internal" if in_docker else "."
data_conn_str = (
    f"DRIVER={{{driver_name}}};SERVER={data_server};DATABASE=event_DWH;"
    f"{'Trusted_Connection=yes;' if not in_docker else 'UID=sa;PWD=EventZella2024!;'}TrustServerCertificate=yes;"
)
data_params = urllib.parse.quote_plus(data_conn_str)
data_engine = create_engine(f"mssql+pyodbc:///?odbc_connect={data_params}", pool_pre_ping=True)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()