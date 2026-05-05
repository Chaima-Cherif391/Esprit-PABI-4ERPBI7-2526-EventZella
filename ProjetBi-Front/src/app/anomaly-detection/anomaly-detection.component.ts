import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { SoundService } from '../services/sound.service';

@Component({
  selector: 'app-anomaly-detection',
  templateUrl: './anomaly-detection.component.html',
  styleUrls: ['./anomaly-detection.component.css']
})
export class AnomalyDetectionComponent implements OnInit {

  formData = {
    price: 120000,
    nbr_reservations: 250,
    nbr_visitors: 4000,
    marketing_spend: 20000,
    market_count: 5,
    rating: 4.5
  };

  isLoading = false;
  anomalyData: any = null;
  dbAnomalyData: any = null;
  currentTab: 'database' | 'simulation' = 'database';
  lastScanTime: Date | null = null;
  errorMessage: string | null = null;

  presets = [
    { name: 'Normal Event', data: { price: 8000, nbr_reservations: 200, nbr_visitors: 3000, marketing_spend: 18000, market_count: 8, rating: 4.0 } },
    { name: 'High Price Outlier', data: { price: 145000, nbr_reservations: 50, nbr_visitors: 1200, marketing_spend: 15000, market_count: 1, rating: 4.5 } }
  ];

  isSoundEnabled = false;

  constructor(
    private router: Router,
    private auth: AuthService,
    private http: HttpClient,
    private soundService: SoundService
  ) { }

  ngOnInit(): void {
    this.isSoundEnabled = this.soundService.getSoundStatus();
    this.scanDatabase();
  }

  scanDatabase() {
    this.isLoading = true;
    const backendUrl = this.auth.getBackendUrl();
    const token = this.auth.getToken() || 'skip';

    this.errorMessage = null;
    this.http.get(`${backendUrl}/api/ml/anomaly-db`, {
      headers: { 'Authorization': `Bearer ${token}` }
    }).subscribe({
      next: (data: any) => {
        this.dbAnomalyData = data;
        this.isLoading = false;
        this.lastScanTime = new Date();
        if (this.isSoundEnabled && data.consensus) {
          this.soundService.speak(`Database scan complete. Detected ${data.consensus.anomalies_detected} critical anomalies across ${data.total_records} records.`);
        }
      },
      error: (err) => {
        console.error(err);
        this.isLoading = false;
        this.errorMessage = err.error?.detail || "Impossible de se connecter à la base de données ou aucune donnée trouvée.";
      }
    });
  }

  setTab(tab: 'database' | 'simulation') {
    this.currentTab = tab;
  }

  isFormValid(): boolean {
    const f = this.formData;
    const isValid = (val: any) => val !== null && val !== undefined && val !== '';
    return (
      isValid(f.price) && f.price >= 0 &&
      isValid(f.nbr_reservations) && f.nbr_reservations >= 0 &&
      isValid(f.nbr_visitors) && f.nbr_visitors >= 0 &&
      isValid(f.marketing_spend) && f.marketing_spend >= 0 &&
      isValid(f.market_count) && f.market_count >= 1 &&
      isValid(f.rating) && f.rating >= 1 && f.rating <= 5
    );
  }

  analyzeAnomaly() {
    this.anomalyData = null; 
    if (!this.isFormValid()) {
      this.errorMessage = "Veuillez remplir tous les champs avec des valeurs valides.";
      return;
    }
    this.isLoading = true;
    this.errorMessage = null;
    const backendUrl = this.auth.getBackendUrl();
    const token = this.auth.getToken() || 'skip';

    this.http.post(`${backendUrl}/api/ml/anomaly-realtime`, this.formData, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      })
    }).subscribe({
      next: (data: any) => {
        this.anomalyData = data;
        this.isLoading = false;
        if (this.isSoundEnabled) {
          if (data.is_anomaly === 1) {
            this.soundService.speak("Alert. Anomaly detected. " + data.explanation);
          } else {
            this.soundService.speak("All metrics are normal. " + data.explanation);
          }
        }
      },
      error: (err) => {
        console.error(err);
        this.isLoading = false;
        this.errorMessage = "Failed to run anomaly analysis.";
      }
    });
  }

  applyPreset(preset: any) {
    this.formData = { ...preset.data };
    this.anomalyData = null;
    if (this.isSoundEnabled) {
      this.soundService.speak(`Applying ${preset.name} template.`);
    }
  }

  goHome(): void {
    const role = this.auth.getRole();
    if (role === 'CEO') {
      this.router.navigate(['/dashboard-ceo']);
    } else {
      this.router.navigate(['/dashboard-marketing']);
    }
  }

  toggleSound(): void {
    this.soundService.toggleSound();
    this.isSoundEnabled = this.soundService.getSoundStatus();
  }
}
