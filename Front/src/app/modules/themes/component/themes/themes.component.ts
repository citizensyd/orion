import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";
import {ThemeService} from "../../services/theme-service";
import {ThemeDTO} from "../../interfaces/theme.interface";
import {CardComponent} from "../card/card.component";
import {NgForOf} from "@angular/common";
import {SubscriptionDTO} from "../../../article/interfaces/subscription.interface";
import {forkJoin} from "rxjs";

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
export class ThemesComponent implements OnInit {
  themes: ThemeDTO[] = [];
  subscriptions: SubscriptionDTO[] | undefined;

  constructor(
    private themeService: ThemeService,
  ) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  // Méthode pour charger les thèmes
  loadThemes(): void {
    forkJoin({
      themes: this.themeService.getAllThemes(),
      subscriptions: this.themeService.getSubscriptionsByUser()
    }).subscribe({
      next: ({ themes, subscriptions }) => {
        // Assurez-vous que subscriptions n'est pas null ou undefined
        const safeSubscriptions = subscriptions || [];
        this.themes = themes.filter(theme => !safeSubscriptions.some(sub => sub.id === theme.id));
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des données', error);
      }
    });


  }

  // Méthode pour gérer la souscription d'un thème
  handleUnsubscribe(themeId: number): void {
    this.themes = this.themes.filter(theme => theme.id !== themeId);
  }
}
