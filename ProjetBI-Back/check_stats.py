from app.database import engine
from sqlalchemy import text
import pandas as pd
query = text("""
    SELECT 
        CAST(price AS FLOAT) as price,
        CAST(nbr_reservations AS FLOAT) as nbr_reservations,
        CAST(nbr_visitors AS FLOAT) as nbr_visitors,
        CAST(marketing_spend AS FLOAT) as marketing_spend,
        CAST(market_count AS FLOAT) as market_count
    FROM FACT_VENTES
""")
with engine.connect() as conn:
    df = pd.read_sql(query, conn)
print(df.describe())
