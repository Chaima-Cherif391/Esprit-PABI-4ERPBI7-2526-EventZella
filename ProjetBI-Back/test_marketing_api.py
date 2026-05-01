import requests
from app.database import SessionLocal
from app.models.user import User
from app.core.security import create_access_token

db = SessionLocal()
ceo = db.query(User).filter(User.role == "CEO").first()
token = create_access_token({"sub": str(ceo.id), "role": ceo.role})
db.close()

headers = {"Authorization": f"Bearer {token}"}
res = requests.get("http://localhost:5000/api/auth/users/marketing", headers=headers)
print("Status:", res.status_code)
print("Response:", res.text)
