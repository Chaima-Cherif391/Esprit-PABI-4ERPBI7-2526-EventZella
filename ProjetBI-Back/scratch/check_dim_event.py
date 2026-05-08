from app.database import engine
from sqlalchemy import text
try:
    with engine.connect() as conn:
        res = conn.execute(text("SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Dim_Event'"))
        columns = [(r[0], r[1]) for r in res]
        print(f"Columns in Dim_Event: {columns}")
except Exception as e:
    print(f"Error: {e}")
