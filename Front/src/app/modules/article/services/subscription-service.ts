import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { combineLatest, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ArticleDTO } from '../interfaces/ArticleDTO.interface';
import { SubscriptionDTO } from '../interfaces/subscription.interface';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl: string = environment.backendUrl;
  private articleUrl: string = environment.articleUrl;



  constructor(private http: HttpClient) { }

  // Récupère tous les articles
  getAllArticles(): Observable<ArticleDTO[]> {
    return this.http.get<ArticleDTO[]>(`${this.articleUrl}/feed`);
  }

  // Récupère les abonnements de l'utilisateur
  getUserSubscriptions(): Observable<SubscriptionDTO[]> {
    return this.http.get<SubscriptionDTO[]>(`${this.apiUrl}/user`);
  }

  // Récupère les articles auxquels l'utilisateur est abonné
  getUserSubscribedArticles(): Observable<ArticleDTO[]> {
    return combineLatest([
      this.getAllArticles(),
      this.getUserSubscriptions()
    ]).pipe(
      map(([articles, subscriptions]) => {
        if (!subscriptions || subscriptions.length === 0) {
          return [];
        }
        const subscribedThemeIds = subscriptions.map(sub => sub.id);
        return articles.filter(article => subscribedThemeIds.includes(article.themeId));
      })
    );
  }
}
