import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {forkJoin, Subscription} from "rxjs";
import {HeaderComponent} from "../../../../component/header/header.component";
import {ThemeDTO} from "../../interfaces/theme.interface";
import {SubscriptionDTO} from "../../../article/interfaces/subscription.interface";
import {ThemeService} from "../../services/theme-service";
import {CardComponent} from "../card/card.component";

@Component({
  selector: 'app-themes',
  standalone: true,
  imports: [
    HeaderComponent,
    CardComponent,
    NgForOf
  ],
  templateUrl: './themes.component.html',
  styleUrl: './themes.component.css'
})
export class ThemesComponent implements OnInit, OnDestroy  {
  themes: ThemeDTO[] = [];
  subscriptions: SubscriptionDTO[] = [];
  private subscriptionsTracker: Subscription;

  constructor(
    private themeService: ThemeService,
  ) {
    this.subscriptionsTracker = new Subscription();
  }

  ngOnInit(): void {
    this.loadThemes();
  }

  // Méthode pour charger les thèmes
  loadThemes(): void {
    const request = forkJoin({
      themes: this.themeService.getAllThemes(),
      subscriptions: this.themeService.getSubscriptionsByUser()
    });

    this.subscriptionsTracker.add(request.subscribe({
      next: ({ themes, subscriptions }) => {
        this.themes = themes;
        this.subscriptions = subscriptions || [];
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des données', error);
        // Potentially handle user notification here
      }
    }));
  }

  // Méthode pour gérer la souscription d'un thème
  handleUnsubscribe(): void {
    this.loadThemes();
  }
  ngOnDestroy(): void {
    this.subscriptionsTracker.unsubscribe();
  }
}
