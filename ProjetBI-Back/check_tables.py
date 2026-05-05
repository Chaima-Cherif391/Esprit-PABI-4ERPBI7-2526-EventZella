from app.database import engine
from sqlalchemy import text
try:
    with engine.connect() as conn:
        res = conn.execute(text('SELECT name FROM sys.tables'))
        tables = [r[0] for r in res]
        print(f"Tables: {tables}")
        
        # Check FACT_VENTES
        if 'FACT_VENTES' in tables:
            count = conn.execute(text('SELECT COUNT(*) FROM FACT_VENTES')).fetchone()[0]
            print(f"FACT_VENTES count: {count}")
        else:
            print("FACT_VENTES not found!")
except Exception as e:
    print(f"Error: {e}")
