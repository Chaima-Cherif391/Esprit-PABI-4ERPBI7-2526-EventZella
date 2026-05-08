from app.database import data_engine
from sqlalchemy import text
try:
    with data_engine.connect() as conn:
        res = conn.execute(text("SELECT name FROM sys.tables WHERE name LIKE 'Dim%' OR name LIKE 'FACT%' ORDER BY name"))
        tables = [r[0] for r in res]
        print(f"Tables found: {tables}")
except Exception as e:
    print(f"Error: {e}")
