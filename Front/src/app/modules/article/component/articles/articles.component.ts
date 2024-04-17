import {Component, OnDestroy, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";
import {NgClass, NgForOf, NgIf, NgStyle} from "@angular/common";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {SubscriptionService} from "../../services/subscription-service";
import {ArticleDTO} from "../../interfaces/ArticleDTO.interface";
import {CardComponent} from "../card/card.component";
import {RouterLink} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    HeaderComponent,
    NgStyle,
    ButtonComponent,
    CardComponent,
    NgForOf,
    NgIf,
    NgClass,
    RouterLink
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent implements OnInit, OnDestroy {
  filteredArticles: ArticleDTO[] = [];
  isAscending: boolean = false;
  noArticlesMessage: string = '';
  private subscriptions: Subscription;

  constructor(private subscriptionService: SubscriptionService) {
    this.subscriptions = new Subscription();
  }

  ngOnInit() {
    this.subscriptions.add(this.subscriptionService.getUserSubscribedArticles().subscribe({
      next: (articles: ArticleDTO[]) => {
        if (articles.length === 0) {
          this.noArticlesMessage = 'Aucun article disponible pour les thèmes auxquels vous êtes abonné.';
        } else {
          this.filteredArticles = articles;
          this.noArticlesMessage = '';
        }
      },
      error: (error: any) => {
        console.error('Erreur lors de la récupération des articles filtrés', error);
        this.noArticlesMessage = 'Erreur lors du chargement des articles.';
      }
    }));
  }
  sortArticles(articles: ArticleDTO[], ascending: boolean): ArticleDTO[] {
    return articles.map(article => ({ ...article, dateParsed: new Date(article.createdAt).getTime() }))
      .sort((a, b) => ascending ? a.dateParsed - b.dateParsed : b.dateParsed - a.dateParsed);
  }

  toggleSortOrder() {
    this.isAscending = !this.isAscending;
    this.filteredArticles = this.sortArticles(this.filteredArticles, this.isAscending);
  }
  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}
