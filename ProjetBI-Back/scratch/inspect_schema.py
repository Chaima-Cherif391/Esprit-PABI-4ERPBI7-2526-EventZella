from app.database import data_engine
from sqlalchemy import text

def list_schema():
    with data_engine.connect() as conn:
        # Get tables
        tables = conn.execute(text("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE'")).fetchall()
        for table in tables:
            t_name = table[0]
            print(f"Table: {t_name}")
            # Get columns
            cols = conn.execute(text(f"SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='{t_name}'")).fetchall()
            for col in cols:
                print(f"  - {col[0]} ({col[1]})")
            print("-" * 20)

if __name__ == "__main__":
    try:
        list_schema()
    except Exception as e:
        print(f"Error: {e}")
