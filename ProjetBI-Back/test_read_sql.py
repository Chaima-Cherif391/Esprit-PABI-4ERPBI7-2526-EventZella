from app.database import engine
from sqlalchemy import text
import pandas as pd
query = text("""
    SELECT TOP 10
        Date_PK,
        CAST(price AS FLOAT) as price,
        CAST(nbr_reservations AS FLOAT) as nbr_reservations,
        CAST(nbr_visitors AS FLOAT) as nbr_visitors,
        CAST(marketing_spend AS FLOAT) as marketing_spend,
        CAST(market_count AS FLOAT) as market_count,
        CAST(rating AS FLOAT) as rating
    FROM FACT_VENTES
    WHERE Date_PK IS NOT NULL
    ORDER BY Date_PK DESC
""")
try:
    with engine.connect() as conn:
        df = pd.read_sql(query, conn)
    print(f"Success! Rows: {len(df)}")
    print(df.head())
except Exception as e:
    print(f"Error: {e}")
