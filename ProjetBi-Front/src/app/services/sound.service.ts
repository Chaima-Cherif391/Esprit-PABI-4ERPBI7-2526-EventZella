import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SoundService {
  private isEnabled = false;
  private synthesis: SpeechSynthesis;
  private currentUtterance: SpeechSynthesisUtterance | null = null;

  constructor() {
    this.synthesis = window.speechSynthesis;
  }

  toggleSound(): boolean {
    this.isEnabled = !this.isEnabled;
    if (!this.isEnabled) {
      this.stop();
    } else {
      // Optional: Give a quick feedback that it's enabled
      this.speak("Voice assistance activated.");
    }
    return this.isEnabled;
  }

  getSoundStatus(): boolean {
    return this.isEnabled;
  }

  speak(text: string) {
    if (!this.isEnabled || !text) return;

    this.stop();
    this.currentUtterance = new SpeechSynthesisUtterance(text);
    this.currentUtterance.lang = 'en-US';
    this.synthesis.speak(this.currentUtterance);
  }

  stop() {
    if (this.synthesis.speaking) {
      this.synthesis.cancel();
    }
  }
}
