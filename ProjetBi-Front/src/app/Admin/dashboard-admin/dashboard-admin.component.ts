import { Component, OnInit, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { SoundService } from '../../services/sound.service';

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit {
  @ViewChild('chatScroll') private chatScrollContainer!: ElementRef;

  powerBiUrl!: SafeResourceUrl;
  grafanaUrl!: SafeResourceUrl;
  displayMode: 'pbi' | 'grafana' = 'pbi';
  isRefreshing: boolean = false;
  iframeKey: number = 0;
  
  fullName = '';
  userRole = '';
  userInitials = '';

  // User Management
  showUsersModal = false;
  allUsers: any[] = [];
  editingUser: any = null;
  newUser = { full_name: '', email: '', password: '', role: 'MARKETING' };
  showAddUserForm = false;

  // Notifications
  notifications: any[] = [];
  unreadNotifs = 0;
  isNotifOpen = false;

  // Chat
  isChatOpen = false;
  isAiMode = true;
  chatInput = '';
  chatMessages: any[] = [];
  marketingMessages: any[] = [];
  aiMessages: any[] = [
    { role: 'ai', text: 'Hello Admin! I am your AI assistant. How can I help you today?', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }
  ];
  unreadMessagesCount = 0;
  isTyping = false;

  // Strategic AI
  showStrategyModal = false;
  isGeneratingReport = false;
  formattedReport = '';

  private BACKEND_URL = '';
  private HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  constructor(
    private auth: AuthService,
    private sanitizer: DomSanitizer,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private soundService: SoundService
  ) {
    this.BACKEND_URL = this.auth.getBackendUrl();
  }

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'Admin User';
    this.userRole = user?.role || 'ADMIN';
    this.userInitials = this.getInitials(this.fullName);
    this.loadDashboardData();
    this.fetchNotificationsCount();
    this.startPolling();
    this.loadAvailableTables();
  }

  availableTables: string[] = [];
  selectedTargetTable: string = 'Dim_Event';

  loadAvailableTables() {
    this.http.get<string[]>(`${this.BACKEND_URL}/api/admin/tables`, { headers: this.getAuthHeaders() }).subscribe({
      next: (res) => { this.availableTables = res; },
      error: (err) => console.error('Failed to load tables', err)
    });
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  getAuthHeaders() {
    return new HttpHeaders()
      .set('ngrok-skip-browser-warning', 'any')
      .set('Authorization', `Bearer ${this.auth.getToken()}`);
  }

  loadDashboardData() {
    this.http.get<any>(`${this.BACKEND_URL}/api/powerbi/embed-info`, { headers: this.getAuthHeaders() }).subscribe({
      next: (res) => {
        if (res.embedUrl) {
          this.powerBiUrl = this.sanitizer.bypassSecurityTrustResourceUrl(res.embedUrl);
        }
        if (res.grafanaUrl) {
          this.grafanaUrl = this.sanitizer.bypassSecurityTrustResourceUrl(res.grafanaUrl);
        }
        this.iframeKey = Date.now();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load Admin dashboards', err)
    });
  }

  setMode(mode: 'pbi' | 'grafana') {
    this.displayMode = mode;
    this.refreshIframe();
  }

  refreshIframe() {
    this.isRefreshing = true;
    this.cdr.detectChanges();
    
    setTimeout(() => {
      this.isRefreshing = false;
      this.iframeKey = Date.now();
      this.cdr.detectChanges();
    }, 50);
  }

  // ── NAVIGATION ──
  openAnomaly() { this.router.navigate(['/anomaly']); }
  openRegression() { this.router.navigate(['/price-regression']); }
  openForecast() { this.router.navigate(['/forecast']); }
  goHome() {
    if (this.displayMode !== 'pbi') {
      this.setMode('pbi');
    } else {
      this.router.navigate(['/dashboard-admin']);
    }
  }

  // ── USER MANAGEMENT ──
  openUsersModal() {
    this.showUsersModal = true;
    this.fetchUsers();
  }

  closeUsersModal() {
    this.showUsersModal = false;
    this.editingUser = null;
    this.showAddUserForm = false;
  }

  fetchUsers() {
    this.http.get<any[]>(`${this.BACKEND_URL}/api/auth/users/all`, { headers: this.getAuthHeaders() }).subscribe({
      next: (users) => {
        this.allUsers = [...users];
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to fetch users', err)
    });
  }

  createUser() {
    if (!this.newUser.full_name || !this.newUser.email || !this.newUser.password) {
      alert("Please fill in all fields");
      return;
    }
    this.http.post(`${this.BACKEND_URL}/api/auth/register`, this.newUser, { headers: this.getAuthHeaders() }).subscribe({
      next: () => {
        this.fetchUsers();
        this.showAddUserForm = false;
        this.newUser = { full_name: '', email: '', password: '', role: 'MARKETING' };
        this.cdr.detectChanges();
      },
      error: (err) => alert('Error creating user: ' + (err.error?.error || 'Unknown error'))
    });
  }

  editUser(user: any) { this.editingUser = { ...user }; }

  saveUser() {
    if (!this.editingUser) return;
    this.http.put(`${this.BACKEND_URL}/api/auth/users/${this.editingUser.id}`, this.editingUser, { headers: this.getAuthHeaders() }).subscribe({
      next: () => {
        this.fetchUsers();
        this.editingUser = null;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to update user', err)
    });
  }

  deleteUser(userId: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.http.delete(`${this.BACKEND_URL}/api/auth/users/${userId}`, { headers: this.getAuthHeaders() }).subscribe({
        next: () => {
          this.fetchUsers();
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Failed to delete user', err)
      });
    }
  }

  // ── NOTIFICATIONS ──
  fetchNotificationsCount() {
    this.http.get<any[]>(`${this.BACKEND_URL}/api/notifications/latest`, { headers: this.getAuthHeaders() }).subscribe({
      next: (res) => {
        this.unreadNotifs = res.filter(n => !n.is_read).length;
        this.cdr.detectChanges();
      }
    });
  }

  toggleNotifs() {
    this.isNotifOpen = !this.isNotifOpen;
    if (this.isNotifOpen) {
      this.http.get<any[]>(`${this.BACKEND_URL}/api/notifications/latest`, { headers: this.getAuthHeaders() }).subscribe({
        next: (res) => {
          this.notifications = [...res];
          this.cdr.detectChanges();
        }
      });
    }
  }

  markNotifsRead() {
    this.http.post(`${this.BACKEND_URL}/api/notifications/mark-read`, {}, { headers: this.getAuthHeaders() }).subscribe(() => {
      this.unreadNotifs = 0;
      this.notifications.forEach(n => n.is_read = true);
      this.cdr.detectChanges();
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

  // ── CHAT ──
  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
    if (this.isChatOpen) {
      this.unreadMessagesCount = 0;
      this.chatMessages = this.isAiMode ? this.aiMessages : this.marketingMessages;
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
      role: 'user', text: userText,
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };
    this.chatMessages.push(newMsg);
    setTimeout(() => this.scrollToBottom(), 100);

    if (this.isAiMode) {
      this.isTyping = true;
      this.http.post<any>(`${this.BACKEND_URL}/api/ai/chat`, { message: userText }, { headers: this.getAuthHeaders() }).subscribe({
        next: (res) => {
          this.isTyping = false;
          const aiMsg = {
            role: 'ai', text: res.answer,
            time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
          };
          this.chatMessages.push(aiMsg);
          this.aiMessages = [...this.chatMessages];
          setTimeout(() => this.scrollToBottom(), 100);
        },
        error: () => { this.isTyping = false; }
      });
    } else {
      const msgData = { sender: 'ADMIN', text: userText, timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) };
      this.http.post(`${this.BACKEND_URL}/api/chat/send`, msgData, { headers: this.getAuthHeaders() }).subscribe(() => {
        this.syncMessages();
      });
    }
  }

  syncMessages() {
    if (this.isAiMode) return;
    this.http.get<any[]>(`${this.BACKEND_URL}/api/chat/sync`, { headers: this.getAuthHeaders() }).subscribe({
      next: (msgs) => {
        const hasNew = msgs.length > (this.isAiMode ? this.marketingMessages.length : this.chatMessages.length);
        if (hasNew && !this.isChatOpen) {
          this.unreadMessagesCount++;
        }
        const formatted = msgs.map(m => ({
          role: m.sender === 'ADMIN' ? 'user' : 'ai',
          text: m.text, time: m.timestamp
        }));
        if (!this.isAiMode) this.chatMessages = formatted;
        else this.marketingMessages = formatted;
        this.cdr.detectChanges();
      }
    });
  }

  startPolling() { setInterval(() => this.syncMessages(), 3000); }
  scrollToBottom() { if (this.chatScrollContainer) this.chatScrollContainer.nativeElement.scrollTop = this.chatScrollContainer.nativeElement.scrollHeight; }

  // ── STRATEGIC AI ──
  generateStrategicReport() {
    this.showStrategyModal = true;
    this.isGeneratingReport = true;
    this.formattedReport = '';
    this.http.get<any>(`${this.BACKEND_URL}/api/ai/strategic-report`, { headers: this.getAuthHeaders() }).subscribe({
      next: (res) => {
        this.isGeneratingReport = false;
        this.formattedReport = res.report.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>').replace(/\n/g, '<br>');
      },
      error: () => {
        this.isGeneratingReport = false;
        this.formattedReport = "Error generating strategic briefing.";
      }
    });
  }
  closeStrategyModal() { this.showStrategyModal = false; }

  // ── SOUND ──
  get isSoundEnabled(): boolean { return this.soundService.getSoundStatus(); }
  toggleSound(): void { this.soundService.toggleSound(); }
  hoverSummary() { if (this.isSoundEnabled) this.soundService.speak("Read dashboard summary."); }
  readSummary() {
    if (this.isSoundEnabled) {
      this.soundService.speak("Welcome to the Admin Dashboard. This central command interface allows you to monitor infrastructure via Grafana and analyze business performance with Power BI. You also have full access to anomaly detection, price optimization, and user management tools.");
    } else {
      alert("Please enable voice assistance in the navbar first.");
    }
  }

  // ── CSV UPLOAD ──
  showUploadModal = false;
  selectedFile: File | null = null;
  isUploading = false;
  isDragging = false;

  openUploadModal() { 
    this.loadAvailableTables();
    this.showUploadModal = true; 
  }
  closeUploadModal() { this.showUploadModal = false; this.selectedFile = null; this.isUploading = false; }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file && file.name.endsWith('.csv')) this.selectedFile = file;
    else alert('Please select a valid CSV file.');
  }

  onDragOver(event: DragEvent) { event.preventDefault(); this.isDragging = true; }
  onDragLeave(event: DragEvent) { event.preventDefault(); this.isDragging = false; }

  onDrop(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
    const file = event.dataTransfer?.files[0];
    if (file && file.name.endsWith('.csv')) this.selectedFile = file;
    else alert('Only CSV files are accepted.');
  }

  uploadCSV() {
    if (!this.selectedFile || !this.selectedTargetTable) return;
    this.isUploading = true;
    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.http.post(`${this.BACKEND_URL}/api/admin/upload-table/${this.selectedTargetTable}`, formData, {
      headers: new HttpHeaders({ 'Authorization': `Bearer ${this.auth.getToken()}` })
    }).subscribe({
      next: (res: any) => {
        this.isUploading = false;
        alert(res.message || 'Data imported successfully!');
        this.closeUploadModal();
      },
      error: (err) => {
        this.isUploading = false;
        alert(err.error?.detail || 'Error during data ingestion.');
      }
    });
  }

  logout() { this.auth.logout(); }
}
