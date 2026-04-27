import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-dashboard-ceo',
  templateUrl: './dashboard-ceo.component.html',
  styleUrl: './dashboard-ceo.component.css'
})
export class DashboardCeoComponent implements OnInit {
  activeRoute: string = '/dashboard-ceo';
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
  chatMessages: any[] = [
    { role: 'ai', text: 'Messagerie instantanée avec le service Marketing. Vos messages sont éphémères.' }
  ];

  private BACKEND_URL = '';
  private readonly HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  constructor(
    private router: Router,
    private auth: AuthService,
    private sanitizer: DomSanitizer,
    private http: HttpClient
  ) {
    this.BACKEND_URL = this.auth.getBackendUrl();
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        if (event.urlAfterRedirects !== '/content-ceo') {
          this.activeRoute = event.urlAfterRedirects;
        }
      });

    this.updatePowerBiUrl(this.activeRoute);
    this.startNotificationPolling();
  }

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'CEO User';
    this.userRole = user?.role || 'CEO';
    this.userInitials = this.getInitials(this.fullName);
    this.startPolling();
  }

  startNotificationPolling() {
    this.checkNotifications();
    setInterval(() => this.checkNotifications(), 15000);
  }

  checkNotifications() {
    this.http.get<{ count: number }>(`${this.BACKEND_URL}/api/notifications/unread-count`, { headers: this.HEADERS }).subscribe({
      next: (res) => {
        this.unreadNotifs = res.count;
      },
      error: (err) => console.error('Notification check failed', err)
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
      error: (err) => console.error('Failed to fetch notifications', err)
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

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  updatePowerBiUrl(route: string) {
    this.iframeKey = 0;
    let url = "";
    if (route === '/content-ceo') {
      url = "https://app.powerbi.com/reportEmbed?reportId=8f607b5c-e506-4bfa-9b0e-04ecd9f03190&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730";
    } else {
      url = "https://app.powerbi.com/reportEmbed?reportId=8f607b5c-e506-4bfa-9b0e-04ecd9f03190&groupId=b0809d6d-120a-46e5-af63-9e12b6f11ef2&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&pageName=df6243e1614b506d8b8f&bookmarkGuid=59c679a7afb0fda4b7b0";
    }
    this.powerBiUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    setTimeout(() => { this.iframeKey = Date.now(); }, 100);
  }

  goHome(): void {
    const role = this.auth.getRole();
    if (role === 'CEO') this.router.navigate(['/dashboard-ceo']);
    else if (role === 'MARKETING') this.router.navigate(['/dashboard-marketing']);
    else this.router.navigate(['/login']);
  }

  navigateTo(route: string): void {
    if (route === '/content-ceo' || route === '/dashboard-ceo') {
      this.activeRoute = route;
      this.updatePowerBiUrl(route);
    } else {
      this.router.navigate([route]);
      this.activeRoute = route;
    }
  }

  openRegression(): void {
    this.router.navigate(['/price-regression']);
  }

  exportPDF(): void {
    // On ouvre directement le lien du backend dans un nouvel onglet
    // Cela évite les erreurs de connexion et de sécurité
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

  unreadMessagesCount = 0;

  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
    if (this.isChatOpen) {
      this.unreadMessagesCount = 0; // Reset quand on ouvre
    }
  }

  sendMessage() {
    if (!this.chatInput.trim()) return;
    const msgData = {
      sender: 'CEO',
      text: this.chatInput,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };

    this.http.post(`${this.BACKEND_URL}/api/chat/send`, msgData, { headers: this.HEADERS }).subscribe({
      next: () => {
        this.chatInput = '';
        this.syncMessages();
      }
    });
  }

  syncMessages() {
    this.http.get<any[]>(`${this.BACKEND_URL}/api/chat/sync`, { headers: this.HEADERS }).subscribe({
      next: (msgs) => {
        // Si le nombre de messages a augmenté et que le chat est fermé
        if (msgs.length > this.chatMessages.length && !this.isChatOpen) {
          // On compte combien de nouveaux messages viennent de l'autre
          const newMsgs = msgs.slice(this.chatMessages.length);
          const fromOther = newMsgs.filter(m => m.sender !== 'CEO').length;
          this.unreadMessagesCount += fromOther;
        }

        this.chatMessages = msgs.map(m => ({
          role: m.sender === 'CEO' ? 'user' : 'ai',
          text: m.text,
          time: m.timestamp
        }));
      }
    });
  }

  startPolling() {
    setInterval(() => {
      this.syncMessages(); // On sync tout le temps pour le compteur
    }, 3000);
  }

  isActive(route: string): boolean {
    return this.activeRoute === route;
  }
}
