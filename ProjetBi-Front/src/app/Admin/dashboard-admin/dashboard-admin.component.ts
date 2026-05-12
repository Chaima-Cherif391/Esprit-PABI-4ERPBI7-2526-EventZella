import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { SoundService } from '../../services/sound.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit, OnDestroy {
  @ViewChild('chatScroll') private chatScrollContainer!: ElementRef;

  powerBiUrl!: SafeResourceUrl;
  grafanaUrl!: SafeResourceUrl;
  displayMode: 'pbi' | 'grafana' = 'pbi';
  isRefreshing: boolean = false;
  iframeKey: number = 0;
  autoRefreshEnabled: boolean = false;
  private autoRefreshInterval: any;

  fullName = '';
  userRole = '';
  userInitials = '';

  // User Management
  showUsersModal = false;
  allUsers: any[] = [];
  editingUser: any = null;
  newUser = { full_name: '', email: '', password: '', role: 'MARKETING' };
  showAddUserForm = false;
  
  // Profile Management
  showProfileModal = false;
  profileForm = { full_name: '', email: '', password: '' };

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

  // Airflow
  showAirflowModal = false;
  airflowStatus: any = { status: 'LOADING...', last_execution: 'N/A' };
  isTriggering = false;

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

  ngOnDestroy() {
    this.stopAutoRefresh();
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

  toggleAutoRefresh() {
    this.autoRefreshEnabled = !this.autoRefreshEnabled;
    if (this.autoRefreshEnabled) {
      this.startAutoRefresh();
    } else {
      this.stopAutoRefresh();
    }
  }

  private startAutoRefresh() {
    this.stopAutoRefresh();
    // Refresh every 60 seconds (adjustable)
    this.autoRefreshInterval = setInterval(() => {
      if (this.displayMode === 'pbi') {
        this.refreshIframe();
      }
    }, 60000);
  }

  private stopAutoRefresh() {
    if (this.autoRefreshInterval) {
      clearInterval(this.autoRefreshInterval);
      this.autoRefreshInterval = null;
    }
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
      Swal.fire({
        title: 'Missing Info',
        text: 'Please fill in all fields',
        icon: 'warning',
        confirmButtonColor: '#16c0de',
        background: '#091623',
        color: '#fff'
      });
      return;
    }
    this.auth.register(this.newUser).subscribe({
      next: (res: any) => {
        Swal.fire({
          title: 'User Created',
          text: 'User account established successfully!',
          icon: 'success',
          timer: 2000,
          showConfirmButton: false,
          background: '#091623',
          color: '#fff'
        });
        this.fetchUsers();
        this.showAddUserForm = false;
        this.newUser = { full_name: '', email: '', password: '', role: 'MARKETING' };
      },
      error: (err: any) => Swal.fire({
        title: 'Creation Failed',
        text: 'Error creating user: ' + (err.error?.error || 'Unknown error'),
        icon: 'error',
        confirmButtonColor: '#ff4757',
        background: '#091623',
        color: '#fff'
      })
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
      this.chatMessages = this.aiMessages;
      setTimeout(() => this.scrollToBottom(), 100);
    }
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
    }
  }

  syncMessages() {
    // Disabled for Admin
    return;
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
        let text = res.report;
        
        // Replace Headers
        text = text.replace(/^### (.*$)/gim, '<h3>$1</h3>');
        text = text.replace(/^#### (.*$)/gim, '<h4>$1</h4>');
        
        // Replace Bold
        text = text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
        
        // Replace Horizontal Rules
        text = text.replace(/^===+$/gim, '<div class="report-divider"></div>');
        
        // Replace Lists (simple approach for li)
        text = text.replace(/^\* (.*$)/gim, '<li>$1</li>');
        
        // Wrap adjacent li tags in ul
        text = text.replace(/(<li>.*<\/li>)/gms, '<ul>$1</ul>');

        // Clean up double br caused by markdown newlines vs formatting
        text = text.replace(/\n/g, '<br>');
        
        this.formattedReport = text;
      },
      error: () => {
        this.isGeneratingReport = false;
        this.formattedReport = "<div class='error-msg'>Error generating strategic briefing. Please check your AI service connection.</div>";
      }
    });
  }
  closeStrategyModal() { this.showStrategyModal = false; }

  // ── AIRFLOW ──
  openAirflowModal() {
    this.showAirflowModal = true;
    this.refreshAirflowStatus();
  }

  closeAirflowModal() {
    this.showAirflowModal = false;
  }

  refreshAirflowStatus() {
    this.http.get<any>(`${this.BACKEND_URL}/api/admin/airflow/status`, { headers: this.getAuthHeaders() }).subscribe({
      next: (res: any) => {
        this.airflowStatus = res;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error("Airflow status error", err);
        this.airflowStatus = { status: 'ERROR', last_execution: 'N/A' };
        this.cdr.detectChanges();
      }
    });
  }

  triggerPipeline() {
    this.isTriggering = true;
    const headers = this.getAuthHeaders();
    this.http.post(`${this.BACKEND_URL}/api/admin/airflow/run`, {}, { headers: this.getAuthHeaders() }).subscribe({
      next: (res: any) => {
        Swal.fire({
          title: 'Success!',
          text: 'Pipeline triggered successfully!',
          icon: 'success',
          timer: 2500,
          showConfirmButton: false,
          background: '#091623',
          color: '#fff'
        });
        this.refreshAirflowStatus();
        this.isTriggering = false;
      },
      error: (err: any) => {
        this.isTriggering = false;
        Swal.fire({
          title: 'Pipeline Error',
          text: "Failed to trigger pipeline: " + (err.error?.detail || "Unknown error"),
          icon: 'error',
          confirmButtonColor: '#ff4757',
          background: '#091623',
          color: '#fff'
        });
      }
    });
  }

  // ── SOUND ──
  get isSoundEnabled(): boolean { return this.soundService.getSoundStatus(); }
  toggleSound(): void { this.soundService.toggleSound(); }
  hoverSummary() { if (this.isSoundEnabled) this.soundService.speak("Read dashboard summary."); }
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
    this.soundService.speak("Welcome to the Admin Dashboard. This central command interface allows you to monitor infrastructure via Grafana and analyze business performance with Power BI. You also have full access to anomaly detection, price optimization, and user management tools.");
  }

  // ── CSV UPLOAD ──
  showUploadModal = false;
  selectedFile: File | null = null;
  isUploading = false;
  isDragging = false;

  // ── OCR POSTER UPLOAD ──
  showOcrModal = false;
  selectedOcrFile: File | null = null;
  isOcrUploading = false;
  ocrResult: any = null;

  openOcrModal() {
    this.showOcrModal = true;
    this.ocrResult = null;
    this.selectedOcrFile = null;
  }
  
  closeOcrModal() {
    this.showOcrModal = false;
    this.selectedOcrFile = null;
    this.isOcrUploading = false;
  }
  
  onOcrFileSelected(event: any) {
    const file = event.target.files[0];
    if (!file) return;
    if (!file.type.match('image.*')) {
      Swal.fire({
        title: 'Invalid File',
        text: 'Please select a valid image file (JPG or PNG).',
        icon: 'error',
        confirmButtonColor: '#ff4757',
        background: '#091623',
        color: '#fff'
      });
      return;
    }
    if (!file.name.toLowerCase().endsWith('.jpg') && !file.name.toLowerCase().endsWith('.png') && !file.name.toLowerCase().endsWith('.jpeg')) {
      Swal.fire({
        title: 'Format Error',
        text: 'Only image files are accepted.',
        icon: 'error',
        confirmButtonColor: '#ff4757',
        background: '#091623',
        color: '#fff'
      });
      return;
    }
    this.selectedOcrFile = file;
  }

  onOcrDragOver(event: DragEvent) { event.preventDefault(); this.isDragging = true; }
  onOcrDragLeave(event: DragEvent) { event.preventDefault(); this.isDragging = false; }
  onOcrDrop(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
    const file = event.dataTransfer?.files[0];
    if (file) {
      if (!file.type.match('image.*')) {
        Swal.fire({ title: 'Invalid File', text: 'Please select a valid image file.', icon: 'error', confirmButtonColor: '#ff4757', background: '#091623', color: '#fff' });
        return;
      }
      this.selectedOcrFile = file;
    }
  }

  uploadOcrImage() {
    if (!this.selectedOcrFile) return;
    this.isOcrUploading = true;
    const formData = new FormData();
    formData.append('file', this.selectedOcrFile);

    const headers = new HttpHeaders({ 'Authorization': `Bearer ${this.auth.getToken()}` });
    this.http.post(`${this.auth.getBackendUrl()}/api/ocr/extract-poster`, formData, { headers }).subscribe({
      next: (res: any) => {
        this.isOcrUploading = false;
        alert(res.message || 'Poster data extracted and inserted to Dim_Event!');
        this.selectedOcrFile = null;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        this.isOcrUploading = false;
        alert('OCR Error: ' + (err.error?.detail || 'Error extracting poster data.'));
        this.cdr.detectChanges();
      }
    });
  }

  openUploadModal() {
    this.loadAvailableTables();
    this.showUploadModal = true;
  }
  closeUploadModal() { this.showUploadModal = false; this.selectedFile = null; this.isUploading = false; }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file && file.name.endsWith('.csv')) this.selectedFile = file;
    else {
      Swal.fire({
        title: 'No File Selected',
        text: 'Please select a valid CSV file.',
        icon: 'warning',
        background: '#091623',
        color: '#fff',
        confirmButtonColor: '#16c0de'
      });
    }
  }

  onDragOver(event: DragEvent) { event.preventDefault(); this.isDragging = true; }
  onDragLeave(event: DragEvent) { event.preventDefault(); this.isDragging = false; }

  onDrop(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
    const file = event.dataTransfer?.files[0];
    if (file) {
      if (file.type !== 'text/csv' && !file.name.endsWith('.csv')) {
        Swal.fire({
          title: 'Invalid File',
          text: 'Only CSV files are accepted.',
          icon: 'error',
          background: '#091623',
          color: '#fff',
          confirmButtonColor: '#ff4757'
        });
        return;
      }
      this.selectedFile = file;
    } else {
      Swal.fire({
        title: 'No File Selected',
        text: 'Please select a valid CSV file.',
        icon: 'warning',
        background: '#091623',
        color: '#fff',
        confirmButtonColor: '#16c0de'
      });
    }
  }

  uploadCSV() {
    if (!this.selectedFile || !this.selectedTargetTable) return;
    this.isUploading = true;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('table_name', this.selectedTargetTable);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.auth.getToken()}`,
      'ngrok-skip-browser-warning': '69420'
    });

    this.http.post(`${this.BACKEND_URL}/api/admin/upload-table/${this.selectedTargetTable}`, formData, { headers: this.getAuthHeaders() }).subscribe({
      next: (res: any) => {
        this.isUploading = false;
        alert(res.message || 'Data imported successfully!');
        this.closeUploadModal();
      },
      error: (err: any) => {
        this.isUploading = false;
        alert('Import Failed: ' + (err.error?.detail || 'Error during data ingestion.'));
      }
    });
  }

  logout() {
    this.stopAutoRefresh();
    this.auth.logout();
  }
}
