from app.database import engine
from sqlalchemy import text
import pandas as pd
query = text("SELECT rating FROM Dim_Evaluation")
with engine.connect() as conn:
    df = pd.read_sql(query, conn)
print(df.describe())
