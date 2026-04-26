from dotenv import load_dotenv
from pathlib import Path
import os

BASE_DIR = Path(__file__).resolve().parent.parent.parent
env_path = BASE_DIR / ".env"
load_dotenv(dotenv_path=env_path)


def _load_properties_file() -> dict:
	properties_path = BASE_DIR / "application.properties"
	values = {}
	if not properties_path.exists():
		return values

	for line in properties_path.read_text(encoding="utf-8").splitlines():
		stripped = line.strip()
		if not stripped or stripped.startswith("#"):
			continue
		if "=" not in stripped:
			continue
		key, value = stripped.split("=", 1)
		values[key.strip()] = value.strip()

	return values


PROPERTIES = _load_properties_file()


def _get_setting(env_key: str, property_key: str, default=None):
	env_value = os.getenv(env_key)
	if env_value not in (None, ""):
		return env_value
	return PROPERTIES.get(property_key, default)


def _to_bool(value, default=False):
	if value is None:
		return default
	return str(value).strip().lower() in {"1", "true", "yes", "on"}


def _resolve_path(value: str, default: Path) -> str:
	if not value:
		return str(default.resolve())

	# Normalize accidental escape sequences from env files.
	normalized = str(value).replace("\\n", "\\")
	path_obj = Path(normalized)
	if not path_obj.is_absolute():
		path_obj = (BASE_DIR / path_obj).resolve()
	return str(path_obj)


def _resolve_models_dir(value: str) -> str:
	default_dir = BASE_DIR / ".." / "n8n+ml" / "n8n+ml"
	candidate = Path(_resolve_path(value, default_dir))
	if (candidate / "fidelity_model.pkl").exists():
		return str(candidate)

	search_root = BASE_DIR.parent
	try:
		for model_file in search_root.rglob("fidelity_model.pkl"):
			return str(model_file.parent.resolve())
	except Exception:
		pass

	return str(candidate)


DATABASE_URL = _get_setting("DATABASE_URL", "database.url")
SECRET_KEY = _get_setting("SECRET_KEY", "security.secret.key")
ALGORITHM = _get_setting("ALGORITHM", "security.algorithm", "HS256")
ACCESS_TOKEN_EXPIRE_MINUTES = int(
	_get_setting("ACCESS_TOKEN_EXPIRE_MINUTES", "security.access.token.expire.minutes", 60)
)

CORS_ALLOWED_ORIGINS = _get_setting(
	"CORS_ALLOWED_ORIGINS",
	"cors.allowed.origins",
	"http://localhost:4200,http://127.0.0.1:4200",
)

ML_MODELS_DIR = _resolve_models_dir(
	_get_setting("ML_MODELS_DIR", "ml.models.dir", "../n8n+ml/n8n+ml")
)

DB_DRIVER = _get_setting("DB_DRIVER", "db.driver", "ODBC Driver 17 for SQL Server")
DB_SERVER = _get_setting("DB_SERVER", "db.server", "localhost")
DB_NAME = _get_setting("DB_NAME", "db.name", "event_DWH")
DB_USER = _get_setting("DB_USER", "db.user", "")
DB_PASSWORD = _get_setting("DB_PASSWORD", "db.password", "")
DB_TRUST_SERVER_CERTIFICATE = _get_setting(
	"DB_TRUST_SERVER_CERTIFICATE", "db.trust.server.certificate", "yes"
)

N8N_WEBHOOK_URL = _get_setting("N8N_WEBHOOK_URL", "n8n.webhook.url", "")
N8N_WEBHOOK_ENABLED = _to_bool(_get_setting("N8N_WEBHOOK_ENABLED", "n8n.webhook.enabled", "false"))