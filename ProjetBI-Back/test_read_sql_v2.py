from app.database import engine
from sqlalchemy import text
import pandas as pd
query = text("""
    SELECT TOP 10
        f.Date_PK,
        CAST(f.price AS FLOAT) as price,
        CAST(f.nbr_reservations AS FLOAT) as nbr_reservations,
        CAST(f.nbr_visitors AS FLOAT) as nbr_visitors,
        CAST(f.marketing_spend AS FLOAT) as marketing_spend,
        CAST(f.market_count AS FLOAT) as market_count,
        CAST(e.rating AS FLOAT) as rating
    FROM FACT_VENTES f
    LEFT JOIN Dim_Evaluation e ON f.id_evaluation = e.id_evaluation
    WHERE f.Date_PK IS NOT NULL
    ORDER BY f.Date_PK DESC
""")
try:
    with engine.connect() as conn:
        df = pd.read_sql(query, conn)
    print(f"Success! Rows: {len(df)}")
    print(df.head())
except Exception as e:
    print(f"Error: {e}")
