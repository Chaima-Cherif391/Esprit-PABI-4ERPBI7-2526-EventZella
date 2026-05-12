import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { SoundService } from '../services/sound.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.css']
})
export class ForecastComponent implements OnInit {
  isLoading = true;
  forecastHtml: SafeResourceUrl | null = null;

  fullName = '';
  userRole = '';
  userInitials = '';

  private BACKEND_URL = '';
  private readonly HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer,
    private router: Router,
    private auth: AuthService,
    private soundService: SoundService
  ) {
    this.BACKEND_URL = this.auth.getBackendUrl();
  }

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'User';
    this.userRole = user?.role || 'Guest';
    this.userInitials = this.getInitials(this.fullName);

    this.loadForecast();
  }

  loadForecast() {
    this.isLoading = true;
    this.http.post(`${this.BACKEND_URL}/api/forecast/trigger`, {}, { responseType: 'text', headers: this.HEADERS }).subscribe({
      next: (html) => {
        this.isLoading = false;
        // Créer un Blob pour forcer l'encodage UTF-8 dans l'iframe
        const blob = new Blob([html], { type: 'text/html;charset=utf-8' });
        const url = window.URL.createObjectURL(blob);
        this.forecastHtml = this.sanitizer.bypassSecurityTrustResourceUrl(url);
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Forecast error:', err);
      }
    });
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  goHome(): void {
    const role = this.auth.getRole();
    if (role === 'CEO') this.router.navigate(['/dashboard-ceo']);
    else if (role === 'MARKETING') this.router.navigate(['/dashboard-marketing']);
    else if (role === 'ADMIN') this.router.navigate(['/dashboard-admin']);
    else this.router.navigate(['/login']);
  }

  openRegression(): void {
    this.router.navigate(['/price-regression']);
  }

  generatePDF(): void {
    Swal.fire({
      title: 'Success!',
      text: 'Le rapport PDF va s\'ouvrir dans un nouvel onglet...',
      icon: 'success',
      timer: 2000,
      showConfirmButton: false,
      background: '#091623',
      color: '#fff'
    });
    const url = `${this.auth.getBackendUrl()}/api/reports/forecast_summary_pdf`;
    window.open(url, '_blank');
  }

  exportExcel(): void {
    const googleSheetsUrl = 'https://docs.google.com/spreadsheets/d/1biKepM8Y2DwwtqiPSqdKnObM7ORJkUY6S746PRCci1w/export?format=xlsx';
    window.open(googleSheetsUrl, '_blank');
  }

  toggleNotifs(): void {
    Swal.fire({
      title: 'Info',
      text: 'Notifications are available on the main dashboard.',
      icon: 'info',
      confirmButtonColor: '#16c0de',
      background: '#091623',
      color: '#fff'
    });
  }

  toggleChat(): void {
    Swal.fire({
      title: 'Info',
      text: 'Direct messages are available on the main dashboard.',
      icon: 'info',
      confirmButtonColor: '#16c0de',
      background: '#091623',
      color: '#fff'
    });
  }

  openUsersModal(): void {
    Swal.fire({
      title: 'Access Denied',
      text: 'User management is available on the main dashboard.',
      icon: 'warning',
      confirmButtonColor: '#ff4757',
      background: '#091623',
      color: '#fff'
    });
  }

  logout(): void {
    this.auth.logout();
  }

  get isSoundEnabled(): boolean {
    return this.soundService.getSoundStatus();
  }

  toggleSound(): void {
    this.soundService.toggleSound();
  }

  hoverSummary() {
    if (this.isSoundEnabled) {
      this.soundService.speak("Read forecast summary.");
    }
  }

  readSummary() {
    if (!this.isSoundEnabled) {
      Swal.fire({
        title: 'Voice Disabled',
        text: 'Please enable voice assistance in the navbar first.',
        icon: 'info',
        confirmButtonColor: '#16c0de',
        background: '#091623',
        color: '#fff'
      });
      return;
    }
    this.soundService.speak("Forecast Analysis: Based on historical data, we project a 15% increase in event bookings for the next quarter. Seasonality trends suggest strong demand for wedding and corporate events in June and December.");
  }
}