from sqlalchemy import text
from app.database import engine

def patch_database():
    print("Tentative d'ajout de la colonne 'data' a la table notifications...")
    try:
        with engine.connect() as conn:
            # SQL pour ajouter la colonne data (NVARCHAR(MAX) pour SQL Server)
            conn.execute(text("ALTER TABLE notifications ADD data NVARCHAR(MAX) NULL"))
            conn.commit()
            print("Colonne 'data' ajoutee avec succes !")
    except Exception as e:
        error_msg = str(e)
        if "Column names in each table must be unique" in error_msg or "existe deja" in error_msg:
            print("La colonne 'data' existe deja.")
        else:
            print(f"Erreur lors de la mise a jour : {e}")

if __name__ == "__main__":
    patch_database()
