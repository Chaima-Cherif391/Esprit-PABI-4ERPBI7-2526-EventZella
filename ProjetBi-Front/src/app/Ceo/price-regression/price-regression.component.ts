import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {
  PredictionRequest,
  PredictionResponse,
  PredictionService
} from '../../services/predictionRegression.service';
import { SoundService } from '../../services/sound.service';
import Swal from 'sweetalert2';

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

  // ── CHAT LOGIC ──
  isChatOpen = false;
  chatInput = '';
  isTyping = false;
  isAiMode = false;
  chatMessages: any[] = [
    { role: 'ai', text: 'Messaging with the Marketing service.', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }
  ];
  aiMessages: any[] = [
    { role: 'ai', text: 'Hello! I am your BI Assistant. Ask me anything about your price predictions.', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }
  ];
  marketingMessages: any[] = [];
  @ViewChild('chatScroll') private chatScrollContainer!: ElementRef;

  // ── STRATEGIC REPORT ──
  showStrategyModal = false;
  isGeneratingReport = false;
  formattedReport = '';

  private BACKEND_URL = '';
  private readonly HEADERS = new HttpHeaders().set('ngrok-skip-browser-warning', 'any');

  constructor(
    private predictionService: PredictionService,
    private router: Router,
    private auth: AuthService,
    private http: HttpClient,
    private soundService: SoundService
  ) {
    this.BACKEND_URL = this.auth.getBackendUrl();
  }

  ngOnInit() {
    const user = this.auth.getUser();
    this.fullName = user?.full_name || 'User';
    this.userRole = user?.role || 'Guest';
    this.userInitials = this.getInitials(this.fullName);
    this.startPolling();
  }

  startPolling() {
    setInterval(() => {
      this.syncMessages();
    }, 3000);
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
    } else if (role === 'ADMIN') {
      this.router.navigate(['/dashboard-admin']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  openForecast(): void {
    this.router.navigate(['/forecast']);
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
    this.isChatOpen = !this.isChatOpen;
    if (this.isChatOpen) {
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
        error: () => {
          this.isTyping = false;
          this.chatMessages.push({ role: 'ai', text: "Error connecting to AI.", time: "Now" });
          setTimeout(() => this.scrollToBottom(), 100);
        }
      });
    } else {
      const msgData = { sender: this.userRole === 'CEO' ? 'CEO' : 'Marketing', text: userText, timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) };
      this.http.post(`${this.BACKEND_URL}/api/chat/send`, msgData, { headers: this.HEADERS }).subscribe({
        next: () => this.syncMessages()
      });
    }
  }

  syncMessages() {
    if (this.isAiMode) return;
    this.http.get<any[]>(`${this.BACKEND_URL}/api/chat/sync`, { headers: this.HEADERS }).subscribe({
      next: (msgs) => {
        const formattedMsgs = msgs.map(m => ({
          role: m.sender === (this.userRole === 'CEO' ? 'CEO' : 'Marketing') ? 'user' : 'ai',
          text: m.text,
          time: m.timestamp
        }));
        if (!this.isAiMode) this.chatMessages = formattedMsgs;
        else this.marketingMessages = formattedMsgs;
      }
    });
  }

  scrollToBottom(): void {
    try {
      if (this.chatScrollContainer) {
        this.chatScrollContainer.nativeElement.scrollTop = this.chatScrollContainer.nativeElement.scrollHeight;
      }
    } catch (err) { }
  }

  closeStrategyModal() {
    this.showStrategyModal = false;
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
    return null;
  }

  predictPrice(): void {
    this.errorMessage = '';
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

    this.predictionService.predictPrice(this.formData).subscribe({
      next: (response: PredictionResponse) => {
        this.result = response;
        this.predictedPrice = response.predicted_price;
        this.loading = false;
      },
      error: () => {
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
    if (this.result) {
      this.soundService.speak(`Price Regression Summary: The recommended price is ${this.result.recommended_price} Dinars. The predicted price is ${this.result.predicted_price} Dinars. The confidence score is ${this.result.confidence_score} percent.`);
    } else {
      this.soundService.speak("Price Regression Summary: This page allows you to predict the optimal price for your event based on market inputs.");
    }
  }
}