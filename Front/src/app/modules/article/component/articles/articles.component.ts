import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";
import {NgClass, NgForOf, NgIf, NgStyle} from "@angular/common";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {SubscriptionService} from "../../services/subscription-service";
import {ArticleDTO} from "../../interfaces/ArticleDTO.interface";
import {CardComponent} from "../card/card.component";
import {RouterLink} from "@angular/router";

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
export class ArticlesComponent implements OnInit {
  filteredArticles: ArticleDTO[] = [];
  isAscending: boolean = false;
  noArticlesMessage: string = '';
  noArticlesTemplate: any;

  constructor(private subscriptionService: SubscriptionService) { }

  ngOnInit() {
    this.subscriptionService.getUserSubscribedArticles().subscribe({
      next: (articles: ArticleDTO[]) => {
        console.log(articles)
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
    });
  }
  sortArticles(articles: ArticleDTO[], ascending: boolean) {
    return articles.sort((a, b) => {
      const dateA = new Date(a.createdAt);
      const dateB = new Date(b.createdAt);
      return ascending ? dateA.getTime() - dateB.getTime() : dateB.getTime() - dateA.getTime();
    });
  }

  toggleSortOrder() {
    this.isAscending = !this.isAscending;
    this.filteredArticles = this.sortArticles(this.filteredArticles, this.isAscending);
  }
}
