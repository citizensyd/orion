import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {forkJoin, Subscription} from "rxjs";
import {HeaderComponent} from "../../../../component/header/header.component";
import {SubscriptionDTO} from "../../../article/interfaces/subscription.interface";
import {CardComponent} from "../card/card.component";
import {ThemeDTO} from "../../../themes/interfaces/theme.interface";
import {ThemeService} from "../../../themes/services/theme-service";
import {ErrorHandlingService} from "../../../../services/error-service";

@Component({
  selector: 'app-themes-profil',
  standalone: true,
  imports: [
    HeaderComponent,
    CardComponent,
    NgForOf
  ],
  templateUrl: './themes-profil.component.html',
  styleUrl: './themes-profil.component.css'
})
export class ThemesProfilComponent implements OnInit, OnDestroy {
  themes: ThemeDTO[] = [];
  subscriptions: SubscriptionDTO[] = [];
  private allSubscriptions: Subscription = new Subscription();

  constructor(
    private themeService: ThemeService, private errorHandlingService: ErrorHandlingService
  ) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  // Méthode pour charger les thèmes
  loadThemes(): void {
    const request = forkJoin({
      themes: this.themeService.getAllThemes(),
      subscriptions: this.themeService.getSubscriptionsByUser()
    });
    this.allSubscriptions.add(request.subscribe({
      next: ({ themes, subscriptions }): void => {
        this.themes = themes;
        this.subscriptions = subscriptions || [];
      },
      error: (): void => {
        this.errorHandlingService.handleError()
      }
    }));
  }

  // Méthode pour gérer la souscription d'un thème
  handleUnsubscribe(): void {
    this.loadThemes();
  }
  ngOnDestroy(): void {
    this.allSubscriptions.unsubscribe();
  }
}
