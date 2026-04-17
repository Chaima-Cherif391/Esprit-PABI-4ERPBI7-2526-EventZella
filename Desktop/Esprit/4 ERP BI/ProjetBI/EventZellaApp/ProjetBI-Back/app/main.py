from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.database import Base, engine
from app.routers import auth

try:
    Base.metadata.create_all(bind=engine)
    print("✅ Tables créées / vérifiées")
except Exception as e:
    print(f"⚠️ Erreur DB : {e}")

app = FastAPI(title="EventZella API", version="1.0.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:4200"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(auth.router)

@app.get("/")
def root():
    return {"message": "EventZella API 🚀"}