from app.database import engine
from sqlalchemy import text
with engine.connect() as conn:
    res = conn.execute(text("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Dim_Evaluation'"))
    print(f"Dim_Evaluation cols: {[r[0] for r in res]}")
    res = conn.execute(text("SELECT TOP 5 * FROM Dim_Evaluation"))
    print(f"Dim_Evaluation samples: {[list(r) for r in res]}")
