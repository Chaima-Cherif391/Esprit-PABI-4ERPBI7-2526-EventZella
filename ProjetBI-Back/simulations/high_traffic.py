import requests
import time

URL = "http://localhost:8000/api/ml/predict-price"

payload = {
    "market_count": 12,
    "nbr_visitors": 500,
    "nbr_reservations": 45,
    "marketing_spend": 2000,
    "rating": 4.5,
    "trend_score": 0.7,
    "growth_rate_pct": 12,
    "capacity_min": 100,
    "capacity_max": 800,
    "season_encoded": 2,
    "event_type_encoded": 1,
    "venue_type_encoded": 3,
    "city_encoded": 4
}

print("Starting high traffic simulation...")

for i in range(100):
    try:
        response = requests.post(URL, json=payload)
        print(f"Request {i+1}/100 - Status: {response.status_code}")
    except Exception as e:
        print(f"Request {i+1}/100 - Error: {e}")

    time.sleep(0.05)

print("High traffic simulation finished.")