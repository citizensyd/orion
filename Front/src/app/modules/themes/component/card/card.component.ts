import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ThemeDTO} from "../../interfaces/theme.interface";
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {ThemeService} from "../../services/theme-service";
import {SubscriptionRequest} from "../../interfaces/subscription-request.interface";

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [
    TruncatePipe
  ],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input() theme!: ThemeDTO;
  @Output() subscribeEvent = new EventEmitter<number>();
  constructor(private themeService: ThemeService) {}
  subscribe(themeId: number): void {
    console.log(themeId)
    if (themeId) {
      const userId = this.themeService.getUserIdFromToken();
      if (userId === null) {
        console.error('L\'utilisateur n\'est pas connecté ou le token n\'est pas valide.');
        return;
      }
      const subscriptionRequest: SubscriptionRequest = {
        themeId,
        userId
      };

      this.themeService.subscribeToTheme(subscriptionRequest).subscribe({
        next: () => {
          this.subscribeEvent.emit(themeId);
        },
        error: error => console.error(error)
      });
    }
  }
}
