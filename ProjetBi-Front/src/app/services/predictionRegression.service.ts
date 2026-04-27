import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

export interface PredictionRequest {
  market_count: number;
  nbr_visitors: number;
  nbr_reservations: number;
  marketing_spend: number;
  rating: number;
  trend_score: number;
  growth_rate_pct: number;
  capacity_min: number;
  capacity_max: number;
  season_encoded: number;
  event_type_encoded: number;
  venue_type_encoded: number;
  city_encoded: number;
}

export interface PredictionResponse {
  predicted_price: number;
  recommended_price: number;
  confidence_score: number;
  expected_demand: string;
  risk_level: string;
  strategic_recommendation: string;
  scenarios: {
    conservative: number;
    balanced: number;
    aggressive: number;
  };
}

@Injectable({
  providedIn: 'root'
})
export class PredictionService {
  private apiUrl = '';

  constructor(private http: HttpClient, private auth: AuthService) {
    this.apiUrl = `${this.auth.getBackendUrl()}/api/ml/predict-price`;
  }

  predictPrice(data: PredictionRequest): Observable<PredictionResponse> {
    return this.http.post<PredictionResponse>(this.apiUrl, data);
  }
}