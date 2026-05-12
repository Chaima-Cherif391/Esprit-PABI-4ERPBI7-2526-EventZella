import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SoundService } from '../../services/sound.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard-marketing',
  templateUrl: './dashboard-marketing.component.html',
  styleUrl: './dashboard-marketing.component.css'
})
export class DashboardMarketingComponent implements OnInit, OnDestroy {
  activeRoute: string = '/dashboard-marketing';
  powerBiUrl!: SafeResourceUrl;
  iframeKey: number = 0;
  unreadNotifs = 0;
  isNotifOpen = false;
  notifications: any[] = [];
  autoRefreshEnabled: boolean = false;
  private autoRefreshInterval: any;

  // Profile Management
  showProfileModal = false;
  profileForm = { full_name: '', email: '', password: '' };

  fullName = '';
  userRole = '';
  userInitials = '';

  // ── CHATBOT LOGIC ──
  isChatOpen = false;
  chatInput = '';
  isTyping = false;
  isAiMode = false;
  chatMessages: any[] = [
    { role: 'ai', text: 'Instant messaging with the CEO. Your messages are ephemeral.', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }
  ];
  aiMessages: any[] = [
    { role: 'ai', text: 'Hello! I am your BI Assistant. Ask me anything about your data (sales, weather, events...).', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }
  ];
  marketingMessages: any[] = [];
  @ViewChild('chatScroll') private chatScrollContainer!: ElementRef;

  scrollToBottom(): void {
    try {
      if (this.chatScrollContainer) {
        this.chatScrollContainer.nativeElement.scrollTop = this.chatScrollContainer.nativeElement.scrollHeight;
      }
    } catch (err) { }
  }

  // ── STRATEGIC REPORT ──
  showStrategyModal = false;
  isGeneratingReport = false;
  formattedReport = '';

  private BACKEND_URL = '';
  private readonly HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  isSoundEnabled = false;

  constructor(
    private router: Router,
    private auth: AuthService,
    private sanitizer: DomSanitizer,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private soundService: SoundService
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
    this.isSoundEnabled = this.soundService.getSoundStatus();
  }

  ngOnDestroy(): void {
    this.stopAutoRefresh();
  }

  openProfileModal() {
    const user = this.auth.getUser();
    this.profileForm = {
      full_name: user?.full_name || '',
      email: user?.email || '',
      password: ''
    };
    this.showProfileModal = true;
  }

  closeProfileModal() {
    this.showProfileModal = false;
  }

  saveProfile() {
    const oldEmail = this.auth.getUser()?.email;
    const isChangingEmail = this.profileForm.email !== oldEmail;
    const isChangingPass = !!this.profileForm.password;

    this.auth.updateProfile(this.profileForm).subscribe({
      next: (res: any) => {
        if (isChangingEmail || isChangingPass) {
          Swal.fire({
            title: 'Success!',
            text: 'Profile updated successfully! Please log in again with your new credentials.',
            icon: 'success',
            confirmButtonColor: '#16c0de',
            background: '#091623',
            color: '#fff'
          }).then(() => {
            this.logout();
          });
        } else {
          Swal.fire({
            title: 'Success!',
            text: 'Profile updated successfully!',
            icon: 'success',
            confirmButtonColor: '#16c0de',
            background: '#091623',
            color: '#fff'
          }).then(() => {
            const updatedUser = this.auth.getUser();
            this.fullName = updatedUser.full_name;
            this.userInitials = this.getInitials(this.fullName);
            this.closeProfileModal();
            this.cdr.detectChanges();
          });
        }
      },
      error: (err) => Swal.fire({
        title: 'Error!',
        text: (err.error?.detail || "Unknown error"),
        icon: 'error',
        confirmButtonColor: '#ff4757',
        background: '#091623',
        color: '#fff'
      })
    });
  }

  toggleSound(): void {
    this.soundService.toggleSound();
    this.isSoundEnabled = this.soundService.getSoundStatus();
  }

  hoverSummary() {
    if (this.isSoundEnabled) {
      this.soundService.speak("Read dashboard summary.");
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
    this.soundService.speak("Marketing summary: Event attendance is peaking during weekends, and our promotional campaigns are yielding high conversion rates. The current focus should be on boosting mid-week engagement through targeted offers.");
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  startNotificationPolling() {
    this.checkNotifications();
    setInterval(() => this.checkNotifications(), 15000);
  }

  checkNotifications() {
    this.http.get<{ count: number }>(`${this.BACKEND_URL}/api/notifications/unread-count`, { headers: this.HEADERS }).subscribe({
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

  updatePowerBiUrl() {
    this.iframeKey = 0;

    this.http.get<any>(`${this.BACKEND_URL}/api/powerbi/embed-info`, { headers: this.getAuthHeaders() }).subscribe({
      next: (res) => {
        this.powerBiUrl = this.sanitizer.bypassSecurityTrustResourceUrl(res.embedUrl);
        setTimeout(() => { this.iframeKey = Date.now(); }, 100);
      },
      error: (err) => console.error('Failed to fetch Power BI info Marketing', err)
    });
  }

  getAuthHeaders() {
    return new HttpHeaders()
      .set('ngrok-skip-browser-warning', 'any')
      .set('Authorization', `Bearer ${this.auth.getToken()}`);
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

  exportExcel(): void {
    const googleSheetsUrl = 'https://docs.google.com/spreadsheets/d/1biKepM8Y2DwwtqiPSqdKnObM7ORJkUY6S746PRCci1w/export?format=xlsx';
    window.open(googleSheetsUrl, '_blank');
  }

  refreshIframe(): void {
    this.iframeKey = 0;
    this.cdr.detectChanges();
    setTimeout(() => {
      this.iframeKey = Date.now();
      this.cdr.detectChanges();
    }, 50);
  }

  toggleAutoRefresh(): void {
    this.autoRefreshEnabled = !this.autoRefreshEnabled;
    if (this.autoRefreshEnabled) {
      this.startAutoRefresh();
    } else {
      this.stopAutoRefresh();
    }
  }

  startAutoRefresh(): void {
    this.autoRefreshInterval = setInterval(() => {
      this.refreshIframe();
    }, 60000);
  }

  stopAutoRefresh(): void {
    if (this.autoRefreshInterval) {
      clearInterval(this.autoRefreshInterval);
    }
  }

  logout(): void {
    this.stopAutoRefresh();
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

  toggleChatMode() {
    this.isAiMode = !this.isAiMode;
    if (this.isAiMode) {
      this.marketingMessages = [...this.chatMessages];
      this.chatMessages = [...this.aiMessages];
    } else {
      this.aiMessages = [...this.chatMessages];
      this.chatMessages = [...this.marketingMessages];
    }
    setTimeout(() => this.scrollToBottom(), 100);
  }

  sendMessage() {
    if (!this.chatInput.trim()) return;

    const userText = this.chatInput;
    this.chatInput = '';

    const newMsg = {
      role: 'user',
      text: userText,
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };
    this.chatMessages.push(newMsg);
    setTimeout(() => this.scrollToBottom(), 100);

    if (this.isAiMode) {
      this.isTyping = true;
      this.http.post<any>(`${this.BACKEND_URL}/api/ai/chat`, { message: userText }, { headers: this.HEADERS }).subscribe({
        next: (res) => {
          this.isTyping = false;
          this.chatMessages.push({
            role: 'ai',
            text: res.answer,
            time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
          });
          this.aiMessages = [...this.chatMessages];
          setTimeout(() => this.scrollToBottom(), 100);
        },
        error: (err) => {
          this.isTyping = false;
          this.chatMessages.push({
            role: 'ai',
            text: "Sorry, I encountered an error. Please check the AI assistant configuration.",
            time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
          });
          setTimeout(() => this.scrollToBottom(), 100);
        }
      });
    } else {
      const msgData = {
        sender: 'MARKETING',
        text: userText,
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
      };

      this.http.post(`${this.BACKEND_URL}/api/chat/send`, msgData, { headers: this.HEADERS }).subscribe({
        next: () => {
          this.syncMessages();
        }
      });
    }
  }

  syncMessages() {
    if (this.isAiMode) return;

    this.http.get<any[]>(`${this.BACKEND_URL}/api/chat/sync`, { headers: this.HEADERS }).subscribe({
      next: (msgs) => {
        const hasNewMessages = msgs.length > (this.isAiMode ? this.marketingMessages.length : this.chatMessages.length);

        if (hasNewMessages && !this.isChatOpen) {
          const baseLen = this.isAiMode ? this.marketingMessages.length : this.chatMessages.length;
          const newMsgs = msgs.slice(baseLen);
          const fromOther = newMsgs.filter(m => m.sender !== 'MARKETING').length;
          this.unreadMessagesCount += fromOther;
        }

        const formattedMsgs = msgs.map(m => ({
          role: m.sender === 'MARKETING' ? 'user' : 'ai',
          text: m.text,
          time: m.timestamp
        }));

        if (!this.isAiMode) {
          this.chatMessages = formattedMsgs;
        } else {
          this.marketingMessages = formattedMsgs;
        }

        this.cdr.detectChanges();

        if (hasNewMessages && this.isChatOpen && !this.isAiMode) {
          setTimeout(() => this.scrollToBottom(), 100);
        }
      }
    });
  }

  startPolling() {
    setInterval(() => {
      this.syncMessages();
    }, 3000);
  }

  isActive(route: string): boolean {
    return this.activeRoute === route;
  }

  // ── STRATEGIC REPORT METHODS ──
  generateStrategicReport() {
    this.showStrategyModal = true;
    this.isGeneratingReport = true;
    this.formattedReport = '';

    this.http.get<any>(`${this.BACKEND_URL}/api/ai/strategic-report`, { headers: this.HEADERS }).subscribe({
      next: (res) => {
        this.isGeneratingReport = false;
        let text = res.report;
        
        // Headers
        text = text.replace(/^### (.*$)/gim, '<h3>$1</h3>');
        text = text.replace(/^#### (.*$)/gim, '<h4>$1</h4>');
        
        // Bold
        text = text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
        
        // Horizontal Rules
        text = text.replace(/^===+$/gim, '<div class="report-divider"></div>');
        
        // Lists
        text = text.replace(/^\* (.*$)/gim, '<li>$1</li>');
        text = text.replace(/(<li>.*<\/li>)/gms, '<ul>$1</ul>');

        // Newlines
        text = text.replace(/\n/g, '<br>');
        
        this.formattedReport = text;
      },
      error: (err) => {
        this.isGeneratingReport = false;
        this.formattedReport = "<div class='error-msg'>Error generating strategic report. Please check AI service status.</div>";
      }
    });
  }

  closeStrategyModal() {
    this.showStrategyModal = false;
  }
}
