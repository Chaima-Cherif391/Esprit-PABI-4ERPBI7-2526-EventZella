import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HttpClient } from '@angular/common/http';
import {
  PredictionRequest,
  PredictionResponse,
  PredictionService
} from '../../services/predictionRegression.service';
import { SoundService } from '../../services/sound.service';

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

  seasons = [
    { label: 'Spring', value: 1 },
    { label: 'Summer', value: 2 },
    { label: 'Autumn', value: 3 },
    { label: 'Winter', value: 4 }
  ];

  eventTypes = [
    { label: 'Wedding', value: 0 },
    { label: 'Conference', value: 1 },
    { label: 'Concert', value: 2 },
    { label: 'Birthday', value: 3 },
    { label: 'Gala', value: 4 }
  ];

  venueTypes = [
    { label: 'Hotel', value: 0 },
    { label: 'Banquet Hall', value: 1 },
    { label: 'Outdoor', value: 2 },
    { label: 'Restaurant', value: 3 }
  ];

  capacityRanges = [
    '50-100',
    '100-200',
    '200-400',
    '200-500',
    '300-500',
    '500-1000',
    '1000-2000'
  ];

  selectedCapacityRange = '300-500';

  marketingSpendOptions = [
    { label: 'Low (0 - 5k TND)', value: '2500' },
    { label: 'Medium (5k - 15k TND)', value: '10000' },
    { label: 'High (15k - 30k TND)', value: '22500' },
    { label: 'Premium (30k+ TND)', value: '40000' }
  ];
  selectedMarketingSpend = '22500';

  visitorOptions = [
    { label: 'Small (0 - 500)', value: '250' },
    { label: 'Medium (500 - 2,000)', value: '1250' },
    { label: 'Large (2,000 - 5,000)', value: '3500' },
    { label: 'Massive (5,000+)', value: '8000' }
  ];
  selectedVisitors = '3500';

  reservationOptions = [
    { label: '0 - 50', value: '25' },
    { label: '50 - 150', value: '100' },
    { label: '150 - 300', value: '225' },
    { label: '300+', value: '450' }
  ];
  selectedReservations = '225';

  cities = [
    { label: 'Tunis', value: 16 },
    { label: 'Sousse', value: 15 },
    { label: 'Sfax', value: 14 },
    { label: 'Hammamet', value: 13 },
    { label: 'Djerba', value: 12 },
    { label: 'Bizerte', value: 11 },
    { label: 'Nabeul', value: 10 },
    { label: 'Monastir', value: 9 },
    { label: 'Mahdia', value: 8 },
    { label: 'Kairouan', value: 7 },
    { label: 'Gabès', value: 6 },
    { label: 'Gafsa', value: 5 },
    { label: 'Tozeur', value: 4 },
    { label: 'Ariana', value: 3 }
  ];

  fullName = '';
  userRole = '';
  userInitials = '';

  constructor(
    private predictionService: PredictionService,
    private router: Router,
    private auth: AuthService,
    private http: HttpClient,
    private soundService: SoundService
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
    const backendUrl = this.auth.getBackendUrl();
    const url = `${backendUrl}/api/pdf/download?token=skip`;
    alert('Le rapport PDF va s\'ouvrir dans un nouvel onglet...');
    window.open(url, '_blank');
  }

  exportExcel(): void {
    const googleSheetsUrl = 'https://docs.google.com/spreadsheets/d/1biKepM8Y2DwwtqiPSqdKnObM7ORJkUY6S746PRCci1w/export?format=xlsx';
    window.open(googleSheetsUrl, '_blank');
  }

  toggleNotifs(): void {
    alert('Notifications are available on the main dashboard.');
  }

  toggleChat(): void {
    alert('Direct messages are available on the main dashboard.');
  }

  openUsersModal(): void {
    alert('User management is available on the main dashboard.');
  }

  logout(): void {
    this.auth.logout();
  }

  validateForm(): string | null {
    const keys = Object.keys(this.formData);
    for (let key of keys) {
      const val = (this.formData as any)[key];
      if (val === null || val === undefined || val === '') {
         return 'Please fill out all fields.';
      }
    }
    if (this.formData.market_count < 0) return "Competitors cannot be negative.";
    if (this.formData.nbr_visitors < 0) return "Number of visitors cannot be negative.";
    if (this.formData.nbr_reservations < 0) return "Number of reservations cannot be negative.";
    if (this.formData.marketing_spend < 0) return "Marketing spend cannot be negative.";
    if (this.formData.rating < 1 || this.formData.rating > 5) return "Rating must be between 1 and 5.";
    if (this.formData.capacity_min < 0) return "Minimum capacity cannot be negative.";
    if (this.formData.capacity_max < 0) return "Maximum capacity cannot be negative.";
    if (this.formData.capacity_max < this.formData.capacity_min) return "Maximum capacity must be greater than or equal to minimum capacity.";
    if (this.formData.season_encoded < 1 || this.formData.season_encoded > 4) return "Season must be between 1 and 4.";
    
    return null;
  }

  predictPrice(): void {
    this.errorMessage = '';
    
    // Convert selected capacity range string to min and max
    if (this.selectedCapacityRange.includes('-')) {
      const parts = this.selectedCapacityRange.split('-');
      this.formData.capacity_min = parseInt(parts[0], 10);
      this.formData.capacity_max = parseInt(parts[1], 10);
    }

    this.formData.marketing_spend = Number(this.selectedMarketingSpend);
    this.formData.nbr_visitors = Number(this.selectedVisitors);
    this.formData.nbr_reservations = Number(this.selectedReservations);

    const validationError = this.validateForm();
    if (validationError) {
      this.errorMessage = validationError;
      return;
    }

    this.loading = true;
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
        this.errorMessage = 'An error occurred during prediction.';
        this.loading = false;
      }
    });
  }

  get isSoundEnabled(): boolean {
    return this.soundService.getSoundStatus();
  }

  toggleSound(): void {
    this.soundService.toggleSound();
  }

  hoverSummary() {
    if (this.isSoundEnabled) {
      this.soundService.speak("Read price regression summary.");
    }
  }

  readSummary() {
    if (this.isSoundEnabled) {
      if (this.result) {
        this.soundService.speak(`Price Regression Summary: The recommended price is ${this.result.recommended_price} Dinars. The predicted price is ${this.result.predicted_price} Dinars. The confidence score is ${this.result.confidence_score} percent with a ${this.result.expected_demand} demand level. Strategic recommendation: ${this.result.strategic_recommendation}`);
      } else {
        this.soundService.speak("Price Regression Summary: This page allows you to predict the optimal price for your event based on market inputs. Please run a prediction first to hear the results.");
      }
    } else {
      alert("Please enable voice assistance in the navbar first.");
    }
  }
}