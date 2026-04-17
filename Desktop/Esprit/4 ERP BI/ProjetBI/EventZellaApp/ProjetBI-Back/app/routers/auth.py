from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User
from app.schemas.user import UserCreate, UserLogin, UserOut, Token
from app.core.security import hash_password, verify_password, create_access_token, decode_token
from fastapi.security import OAuth2PasswordBearer

router = APIRouter(prefix="/auth", tags=["Authentication"])
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/auth/login")

VALID_ROLES = ["CEO", "MARKETING"]

# ──────────────────────────────────────────
# REGISTER
# ──────────────────────────────────────────
@router.post("/register", response_model=UserOut, status_code=201)
def register(payload: UserCreate, db: Session = Depends(get_db)):

    # Vérifier rôle valide
    if payload.role not in VALID_ROLES:
        raise HTTPException(
            status_code=400,
            detail=f"Rôle invalide. Choisir parmi : {VALID_ROLES}"
        )

    # Vérifier email unique
    existing = db.query(User).filter(User.email == payload.email).first()
    if existing:
        raise HTTPException(status_code=400, detail="Email déjà utilisé")

    user = User(
        full_name=payload.full_name,
        email=payload.email,
        password=hash_password(payload.password),
        role=payload.role
    )
    db.add(user)
    db.commit()
    db.refresh(user)
    return user

# ──────────────────────────────────────────
# LOGIN
# ──────────────────────────────────────────
@router.post("/login", response_model=Token)
def login(payload: UserLogin, db: Session = Depends(get_db)):
    user = db.query(User).filter(User.email == payload.email).first()

    if not user or not verify_password(payload.password, user.password):
        raise HTTPException(status_code=401, detail="Email ou mot de passe incorrect")

    if not user.is_active:
        raise HTTPException(status_code=403, detail="Compte désactivé")

    token = create_access_token({"sub": str(user.id), "role": user.role})
    return {"access_token": token, "token_type": "bearer", "user": user}

# ──────────────────────────────────────────
# GET CURRENT USER
# ──────────────────────────────────────────
@router.get("/me", response_model=UserOut)
def get_me(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    try:
        payload = decode_token(token)
        user_id = int(payload.get("sub"))
    except Exception:
        raise HTTPException(status_code=401, detail="Token invalide")

    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="Utilisateur introuvable")
    return user

# ──────────────────────────────────────────
# ROUTE PROTÉGÉE CEO SEULEMENT
# ──────────────────────────────────────────
@router.get("/ceo-only")
def ceo_dashboard(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    try:
        payload = decode_token(token)
        user_id = int(payload.get("sub"))
    except Exception:
        raise HTTPException(status_code=401, detail="Token invalide")

    user = db.query(User).filter(User.id == user_id).first()
    if not user or user.role != "CEO":
        raise HTTPException(status_code=403, detail="Accès réservé au CEO")

    return {"message": f"Bienvenue CEO {user.full_name} 👑"}

# ──────────────────────────────────────────
# ROUTE PROTÉGÉE MARKETING SEULEMENT
# ──────────────────────────────────────────
@router.get("/marketing-only")
def marketing_dashboard(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    try:
        payload = decode_token(token)
        user_id = int(payload.get("sub"))
    except Exception:
        raise HTTPException(status_code=401, detail="Token invalide")

    user = db.query(User).filter(User.id == user_id).first()
    if not user or user.role != "MARKETING":
        raise HTTPException(status_code=403, detail="Accès réservé au Marketing")

    return {"message": f"Bienvenue {user.full_name} 📊"}