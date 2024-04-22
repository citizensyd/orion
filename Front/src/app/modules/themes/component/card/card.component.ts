import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import {ThemeDTO} from "../../interfaces/theme.interface";
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {ThemeService} from "../../services/theme-service";
import {SubscriptionRequest} from "../../interfaces/subscription-request.interface";
import {NgClass} from "@angular/common";
import {SubscriptionDTO} from "../../../article/interfaces/subscription.interface";
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
  constructor(private themeService: ThemeService, private errorHandlingService: ErrorHandlingService) {}
  subscribe(themeId: number): void {
    if (themeId) {
      const userId: number|null = this.themeService.getUserIdFromToken();
      if (userId === null) {
        this.errorHandlingService.handleError()
        return;
      }
      const subscriptionRequest: SubscriptionRequest = {
        themeId,
        userId
      };

      this.subscriptionsTracker.add(this.themeService.subscribeToTheme(subscriptionRequest).subscribe({
        next: (): void => {
          this.subscribeEvent.emit();
        },
        error: error => this.errorHandlingService.handleError()
      }));
    }
  }
  isSubscribed(themeId: number): boolean {
    return <boolean>this.subscriptions?.some(sub => sub.id === themeId);
  }
  ngOnDestroy(): void {
    this.subscriptionsTracker.unsubscribe();
  }
}
