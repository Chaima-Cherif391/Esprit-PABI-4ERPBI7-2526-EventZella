from app.database import engine
from sqlalchemy import text
with engine.connect() as conn:
    res = conn.execute(text('SELECT COUNT(*) FROM FACT_VENTES WHERE id_evaluation IS NOT NULL')).fetchone()
    print(f"FACT_VENTES with eval: {res[0]}")
    res = conn.execute(text('SELECT COUNT(*) FROM FACT_VENTES')).fetchone()
    print(f"FACT_VENTES total: {res[0]}")
