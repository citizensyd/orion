import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {NgClass} from "@angular/common";
import {SubscriptionDTO} from "../../../article/interfaces/subscription.interface";
import {ThemeDTO} from "../../../themes/interfaces/theme.interface";
import {ThemeService} from "../../../themes/services/theme-service";
import {SubscriptionRequest} from "../../../themes/interfaces/subscription-request.interface";

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [
    TruncatePipe,
    NgClass
  ],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input() theme!: ThemeDTO;
  @Input() subscriptions!: SubscriptionDTO[];
  @Output() subscribeEvent = new EventEmitter<number>();
  constructor(private themeService: ThemeService) {}
  unSubscribe(themeId: number): void {
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

      this.themeService.unSubscribeToTheme(subscriptionRequest).subscribe({
        next: () => {
          this.subscribeEvent.emit();
        },
        error: (error: any) => console.error(error)
      });
    }
  }
  isSubscribed(themeId: number): boolean {
    return <boolean>this.subscriptions?.some(sub => sub.id === themeId);
  }
  isUnsubscribed(themeId: number): boolean {
    return !this.isSubscribed(themeId);
  }
}
