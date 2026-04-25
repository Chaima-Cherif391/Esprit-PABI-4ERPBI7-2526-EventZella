import json
from urllib import request as urllib_request

from app.core.config import N8N_WEBHOOK_ENABLED, N8N_WEBHOOK_URL


class N8nService:
    def __init__(self):
        self.enabled = N8N_WEBHOOK_ENABLED and bool(N8N_WEBHOOK_URL)
        self.webhook_url = N8N_WEBHOOK_URL

    def emit_prediction_event(self, payload: dict):
        if not self.enabled:
            return

        body = json.dumps(payload).encode("utf-8")
        req = urllib_request.Request(
            self.webhook_url,
            data=body,
            headers={"Content-Type": "application/json"},
            method="POST",
        )
        # n8n notifications should never block API responses.
        try:
            urllib_request.urlopen(req, timeout=4)
        except Exception:
            return
