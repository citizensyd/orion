import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {NgClass} from "@angular/common";
import {SubscriptionDTO} from "../../../article/interfaces/subscription.interface";
import {ThemeDTO} from "../../../themes/interfaces/theme.interface";
import {ThemeService} from "../../../themes/services/theme-service";
import {SubscriptionRequest} from "../../../themes/interfaces/subscription-request.interface";
import {Subscription} from "rxjs";
import {ErrorHandlingService} from "../../../../services/error-service";

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
export class CardComponent implements OnDestroy {
  @Input() theme!: ThemeDTO;
  @Input() subscriptions!: SubscriptionDTO[];
  @Output() subscribeEvent: EventEmitter<number> = new EventEmitter<number>();
  private subscriptionsTracker: Subscription = new Subscription();

  constructor(private themeService: ThemeService, private errorHandlingService: ErrorHandlingService) {
  }

  unSubscribe(themeId: number): void {
    if (themeId) {
      const userId: number| null = this.themeService.getUserIdFromToken();
      if (userId === null) {
        this.errorHandlingService.handleError()
        return;
      }
      const subscriptionRequest: SubscriptionRequest = {
        themeId,
        userId
      };

      this.subscriptionsTracker.add(this.themeService.unSubscribeToTheme(subscriptionRequest).subscribe({
        next: (): void => {
          this.subscribeEvent.emit();
        },
        error: () => this.errorHandlingService.handleError()
      }));
    }
  }

  ngOnDestroy(): void {
    this.subscriptionsTracker.unsubscribe();
  }

  isSubscribed(themeId: number): boolean {
    return <boolean>this.subscriptions?.some(sub => sub.id === themeId);
  }

  isUnsubscribed(themeId: number): boolean {
    return !this.isSubscribed(themeId);
  }
}
