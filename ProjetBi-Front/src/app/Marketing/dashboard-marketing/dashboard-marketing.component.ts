import { Component, OnInit, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
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
  chatMessages: any[] = [
    { role: 'ai', text: 'Messagerie instantanée avec le CEO. Vos messages sont éphémères.', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }
  ];
  @ViewChild('chatScroll') private chatScrollContainer!: ElementRef;

  scrollToBottom(): void {
    try {
      if (this.chatScrollContainer) {
        this.chatScrollContainer.nativeElement.scrollTop = this.chatScrollContainer.nativeElement.scrollHeight;
      }
    } catch(err) { }
  }

  private BACKEND_URL = '';
  private readonly HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  constructor(
    private router: Router,
    private auth: AuthService,
    private sanitizer: DomSanitizer,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {
    this.BACKEND_URL = this.auth.getBackendUrl();
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.activeRoute = event.urlAfterRedirects;
        this.updatePowerBiUrl();
      });

    this.updatePowerBiUrl();
    this.startNotificationPolling();
  }

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'Marketing User';
    this.userRole = user?.role || 'MARKETING';
    this.userInitials = this.getInitials(this.fullName);
    this.startPolling();
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  startNotificationPolling() {
    this.checkNotifications();
    setInterval(() => this.checkNotifications(), 15000);
  }

  checkNotifications() {
    this.http.get<{count: number}>(`${this.BACKEND_URL}/api/notifications/unread-count`, { headers: this.HEADERS }).subscribe({
      next: (res) => {
        this.unreadNotifs = res.count;
        this.cdr.detectChanges();
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
      next: (res) => {
        this.notifications = [...res];
        this.cdr.detectChanges();
      },
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
      this.cdr.detectChanges();
      this.fetchNotifications();
    });
  }

  openForecast() {
    this.router.navigate(['/forecast']);
  }

  updatePowerBiUrl() {
    this.iframeKey = 0;
    const url = "https://app.powerbi.com/reportEmbed?reportId=0768d703-3c6b-48a0-99f8-c5e1b924b1e7&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730&pageName=df6243e1614b506d8b8f&filterPaneEnabled=false&navContentPaneEnabled=false";
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

  unreadMessagesCount = 0;

  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
    if (this.isChatOpen) {
      this.unreadMessagesCount = 0;
      setTimeout(() => this.scrollToBottom(), 100);
    }
  }

  sendMessage() {
    if (!this.chatInput.trim()) return;
    const msgData = {
      sender: 'MARKETING',
      text: this.chatInput,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };
    
    this.http.post(`${this.BACKEND_URL}/api/chat/send`, msgData, { headers: this.HEADERS }).subscribe({
      next: () => {
        this.chatInput = '';
        this.cdr.detectChanges();
        this.syncMessages();
        setTimeout(() => this.scrollToBottom(), 100);
      }
    });
  }

  syncMessages() {
    this.http.get<any[]>(`${this.BACKEND_URL}/api/chat/sync`, { headers: this.HEADERS }).subscribe({
      next: (msgs) => {
        const hasNewMessages = msgs.length > this.chatMessages.length;
        
        if (hasNewMessages && !this.isChatOpen) {
          const newMsgs = msgs.slice(this.chatMessages.length);
          const fromOther = newMsgs.filter(m => m.sender !== 'MARKETING').length;
          this.unreadMessagesCount += fromOther;
        }

        this.chatMessages = msgs.map(m => ({
          role: m.sender === 'MARKETING' ? 'user' : 'ai',
          text: m.text,
          time: m.timestamp
        }));
        this.cdr.detectChanges();
        
        if (hasNewMessages && this.isChatOpen) {
          setTimeout(() => this.scrollToBottom(), 100);
        }
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
