import { Directive, HostListener, Input } from '@angular/core';
import { SoundService } from '../services/sound.service';

@Directive({
  selector: '[appSoundVisual]'
})
export class SoundVisualDirective {
  @Input('appSoundVisual') description: string = '';

  constructor(private soundService: SoundService) { }

  @HostListener('mouseenter') onMouseEnter() {
    if (this.description) {
      this.soundService.speak(this.description);
    }
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.soundService.stop();
  }
}
