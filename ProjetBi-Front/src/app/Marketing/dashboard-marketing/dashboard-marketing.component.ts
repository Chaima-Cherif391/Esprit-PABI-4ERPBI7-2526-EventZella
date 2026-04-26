import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-dashboard-marketing',
  templateUrl: './dashboard-marketing.component.html',
  styleUrl: './dashboard-marketing.component.css'
})
export class DashboardMarketingComponent implements OnInit {
  activeRoute: string = '/dashboard-marketing';
  powerBiUrl!: SafeResourceUrl;
  iframeKey: number = 0;
  unreadNotifs = 0;
  isNotifOpen = false;
  notifications: any[] = [];

  fullName = '';
  userRole = '';
  userInitials = '';

  // ── CHATBOT LOGIC ──
  isChatOpen = false;
  chatInput = '';
  isTyping = false;
  chatMessages: { role: 'ai' | 'user', text: string }[] = [
    { role: 'ai', text: 'Bonjour ! Je suis votre assistant IA Marketing. Comment puis-je vous aider avec vos rapports Power BI ?' }
  ];

  private readonly BACKEND_URL = 'http://localhost:8000';
  private readonly HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  constructor(
    private router: Router,
    private auth: AuthService,
    private sanitizer: DomSanitizer,
    private http: HttpClient
  ) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.activeRoute = event.urlAfterRedirects;
        this.updatePowerBiUrl();
      });

    this.updatePowerBiUrl();
    this.startNotificationPolling();
  }

  startNotificationPolling() {
    this.checkNotifications();
    setInterval(() => this.checkNotifications(), 15000);
  }

  checkNotifications() {
    this.http.get<{count: number}>(`${this.BACKEND_URL}/api/notifications/unread-count`, { headers: this.HEADERS }).subscribe({
      next: (res) => {
        this.unreadNotifs = res.count;
      },
      error: (err) => console.error('Notification check failed Marketing', err)
    });
  }

  toggleNotifs() {
    this.isNotifOpen = !this.isNotifOpen;
    if (this.isNotifOpen) {
      this.fetchNotifications();
    }
  }

  fetchNotifications() {
    this.http.get<any[]>(`${this.BACKEND_URL}/api/notifications/latest`, { headers: this.HEADERS }).subscribe({
      next: (res) => this.notifications = res,
      error: (err) => console.error('Failed to fetch notifications Marketing', err)
    });
  }

  viewNotif(n: any) {
    if (n.data) {
      const win = window.open();
      if (win) {
        if (n.data.startsWith('data:text/html')) {
          win.document.write(atob(n.data.split(',')[1]));
        } else {
          win.location.href = n.data;
        }
      }
    }
    this.isNotifOpen = false;
  }

  markNotifsRead() {
    this.http.post(`${this.BACKEND_URL}/api/notifications/mark-read`, {}, { headers: this.HEADERS }).subscribe(() => {
      this.unreadNotifs = 0;
      this.fetchNotifications();
    });
  }

  openForecast() {
    this.router.navigate(['/forecast']);
  }

  updatePowerBiUrl() {
    this.iframeKey = 0;
    const url = "https://app.powerbi.com/reportEmbed?reportId=8f607b5c-e506-4bfa-9b0e-04ecd9f03190&groupId=b0809d6d-120a-46e5-af63-9e12b6f11ef2&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&pageName=df6243e1614b506d8b8f&bookmarkGuid=29b1980383c08412c81e";
    this.powerBiUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    setTimeout(() => { this.iframeKey = Date.now(); }, 100);
  }

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'Marketing User';
    this.userRole = user?.role || 'Marketing';
    this.userInitials = this.getInitials(this.fullName);
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  goHome(): void {
    const role = this.auth.getRole();
    if (role === 'CEO') this.router.navigate(['/dashboard-ceo']);
    else if (role === 'MARKETING') this.router.navigate(['/dashboard-marketing']);
    else this.router.navigate(['/login']);
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
    this.activeRoute = route;
  }

  openRegression(): void {
    this.router.navigate(['/price-regression']);
  }

  exportPDF(): void {
    const url = `${this.BACKEND_URL}/api/pdf/download?token=skip`;
    alert('Le rapport PDF va s\'ouvrir dans un nouvel onglet...');
    window.open(url, '_blank');
  }

  exportExcel(): void {
    const googleSheetsUrl = 'https://docs.google.com/spreadsheets/d/1biKepM8Y2DwwtqiPSqdKnObM7ORJkUY6S746PRCci1w/export?format=xlsx';
    window.open(googleSheetsUrl, '_blank');
  }

  logout(): void {
    this.auth.logout();
  }

  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
  }

  sendMessage() {
    if (!this.chatInput.trim()) return;
    const userMsg = this.chatInput;
    this.chatMessages.push({ role: 'user', text: userMsg });
    this.chatInput = '';
    this.isTyping = true;

    this.http.post<{ answer: string }>(`${this.BACKEND_URL}/api/chat`, { message: userMsg }, { headers: this.HEADERS }).subscribe({
      next: (res) => {
        this.isTyping = false;
        this.chatMessages.push({ role: 'ai', text: res.answer || "Désolé, je n'ai pas pu analyser ces données." });
      },
      error: () => {
        this.isTyping = false;
        this.chatMessages.push({ role: 'ai', text: "Erreur de connexion avec l'assistant IA via le tunnel." });
      }
    });
  }

  isActive(route: string): boolean {
    return this.activeRoute === route;
  }
}
