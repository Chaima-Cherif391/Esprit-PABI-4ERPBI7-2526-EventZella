import { Component } from '@angular/core';
import {
  PredictionRequest,
  PredictionResponse,
  PredictionService
} from '../../services/predictionRegression.service';

@Component({
  selector: 'app-price-regression',
  templateUrl: './price-regression.component.html',
  styleUrls: ['./price-regression.component.css']
})
export class PriceRegressionComponent {
  formData: PredictionRequest = {
    market_count: 2,
    nbr_visitors: 4000,
    nbr_reservations: 250,
    marketing_spend: 25000,
    rating: 4,
    trend_score: 0,
    growth_rate_pct: 0,
    capacity_min: 300,
    capacity_max: 500,
    season_encoded: 1,
    event_type_encoded: 1,
    venue_type_encoded: 0,
    city_encoded: 16
  };

  result: PredictionResponse | null = null;
  predictedPrice: number | null = null;
  loading = false;
  errorMessage = '';

  constructor(private predictionService: PredictionService) {}

  predictPrice(): void {
    this.loading = true;
    this.errorMessage = '';
    this.predictedPrice = null;
    this.result = null;

    const payload: PredictionRequest = {
      market_count: Number(this.formData.market_count),
      nbr_visitors: Number(this.formData.nbr_visitors),
      nbr_reservations: Number(this.formData.nbr_reservations),
      marketing_spend: Number(this.formData.marketing_spend),
      rating: Number(this.formData.rating),
      trend_score: Number(this.formData.trend_score),
      growth_rate_pct: Number(this.formData.growth_rate_pct),
      capacity_min: Number(this.formData.capacity_min),
      capacity_max: Number(this.formData.capacity_max),
      season_encoded: Number(this.formData.season_encoded),
      event_type_encoded: Number(this.formData.event_type_encoded),
      venue_type_encoded: Number(this.formData.venue_type_encoded),
      city_encoded: Number(this.formData.city_encoded)
    };

    console.log('Payload envoye au backend unifie :');
    console.log(JSON.stringify(payload, null, 2));

    this.predictionService.predictPrice(payload).subscribe({
      next: (response: PredictionResponse) => {
        console.log('Réponse reçue :');
        console.log(JSON.stringify(response, null, 2));

        this.result = response;
        this.predictedPrice = response.predicted_price;
        this.loading = false;
      },
      error: (error) => {
        console.error('Prediction error:', error);
        this.errorMessage = 'Erreur lors de la prédiction.';
        this.loading = false;
      }
    });
  }
}