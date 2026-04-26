from passlib.context import CryptContext
from jose import JWTError, jwt
from datetime import datetime, timedelta
from app.core.config import SECRET_KEY, ALGORITHM, ACCESS_TOKEN_EXPIRE_MINUTES

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

def hash_password(password: str) -> str:
    if password is None:
        raise ValueError("Password is required")

    password_text = str(password)
    if not password_text.strip():
        raise ValueError("Password cannot be empty")

    # bcrypt only supports passwords up to 72 bytes.
    if len(password_text.encode("utf-8")) > 72:
        raise ValueError("Password cannot be longer than 72 bytes")

    try:
        return pwd_context.hash(password_text)
    except Exception as exc:
        raise RuntimeError("Password hashing failed") from exc

def verify_password(plain: str, hashed: str) -> bool:
    if plain is None or hashed is None:
        return False
    try:
        return pwd_context.verify(plain, hashed)
    except Exception:
        return False

def create_access_token(data: dict) -> str:
    to_encode = data.copy()
    expire = datetime.utcnow() + timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
    to_encode.update({"exp": expire})
    return jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)

def decode_token(token: str) -> dict:
    return jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])