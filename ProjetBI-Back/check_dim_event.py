import sys
import os
sys.path.append(os.path.dirname(__file__))
from app.database import data_engine
from sqlalchemy import text

try:
    with data_engine.connect() as conn:
        res = conn.execute(text("SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Dim_Event'")).fetchall()
        print("Dim_Event columns:", res)
except Exception as e:
    print(e)
