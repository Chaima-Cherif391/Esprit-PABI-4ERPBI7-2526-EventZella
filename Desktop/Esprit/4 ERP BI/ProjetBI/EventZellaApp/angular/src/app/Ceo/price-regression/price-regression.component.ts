import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HttpClient } from '@angular/common/http';
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
export class PriceRegressionComponent implements OnInit {
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

  fullName = '';
  userRole = '';
  userInitials = '';

  constructor(
    private predictionService: PredictionService,
    private router: Router,
    private auth: AuthService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'User';
    this.userRole = user?.role || 'Guest';
    this.userInitials = this.getInitials(this.fullName);
  }

  getInitials(name: string): string {
    return name
      .split(' ')
      .map(n => n[0])
      .join('')
      .toUpperCase()
      .substring(0, 2);
  }

  goHome(): void {
    const role = this.auth.getRole();
    if (role === 'CEO') {
      this.router.navigate(['/dashboard-ceo']);
    } else if (role === 'MARKETING') {
      this.router.navigate(['/dashboard-marketing']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  openForecast(): void {
    this.router.navigate(['/forecast']);
  }

  exportPDF(): void {
    const backendUrl = 'http://localhost:8000';
    const url = `${backendUrl}/api/pdf/download?token=skip`;
    alert('Le rapport PDF va s\'ouvrir dans un nouvel onglet...');
    window.open(url, '_blank');
  }

  exportExcel(): void {
    // Lien de téléchargement direct en format Excel (.xlsx)
    const googleSheetsUrl = 'https://docs.google.com/spreadsheets/d/1biKepM8Y2DwwtqiPSqdKnObM7ORJkUY6S746PRCci1w/export?format=xlsx';
    window.open(googleSheetsUrl, '_blank');
  }

  logout(): void {
    this.auth.logout();
  }

  predictPrice(): void {
    this.loading = true;
    this.errorMessage = '';
    this.predictedPrice = null;
    this.result = null;

    const payload: PredictionRequest = { ...this.formData };
    Object.keys(payload).forEach(key => {
      (payload as any)[key] = Number((payload as any)[key]);
    });

    this.predictionService.predictPrice(payload).subscribe({
      next: (response: PredictionResponse) => {
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