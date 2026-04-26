import pyodbc

# Variante 1
try:
    conn = pyodbc.connect(
        "DRIVER={ODBC Driver 17 for SQL Server};"
        "SERVER=localhost\\MSSQLSERVER1;"
        "DATABASE=event_DWH;"
        "Trusted_Connection=yes;"
        "TrustServerCertificate=yes;"
    )
    print("OK Variante 1 - localhost")
except Exception as e:
    print("FAIL Variante 1 :", e)

# Variante 2
try:
    conn = pyodbc.connect(
        "DRIVER={ODBC Driver 17 for SQL Server};"
        "SERVER=.\\MSSQLSERVER1;"
        "DATABASE=event_DWH;"
        "Trusted_Connection=yes;"
        "TrustServerCertificate=yes;"
    )
    print("OK Variante 2 - point")
except Exception as e:
    print("FAIL Variante 2 :", e)

# Variante 3
try:
    conn = pyodbc.connect(
        "DRIVER={ODBC Driver 17 for SQL Server};"
        "SERVER=DESKTOP-4JM6NP\\MSSQLSERVER1;"
        "DATABASE=event_DWH;"
        "Trusted_Connection=yes;"
        "TrustServerCertificate=yes;"
    )
    print("OK Variante 3 - nom complet")
except Exception as e:
    print("FAIL Variante 3 :", e)