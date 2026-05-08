from app.database import data_engine
from sqlalchemy import text
try:
    with data_engine.connect() as conn:
        res = conn.execute(text("SELECT is_identity FROM sys.columns WHERE object_id = OBJECT_ID('Dim_Event') AND name = 'id_event'"))
        is_identity = res.fetchone()
        print(f"Is id_event identity? {is_identity[0] if is_identity else 'Not found'}")
except Exception as e:
    print(f"Error: {e}")
