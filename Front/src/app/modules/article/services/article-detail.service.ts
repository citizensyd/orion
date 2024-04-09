// src/app/services/article.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticleDTO } from '../interfaces/ArticleDTO.interface';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = `${environment.articleUrl}`;

  constructor(private http: HttpClient) {}

  getArticleById(id: number): Observable<ArticleDTO> {
    return this.http.get<ArticleDTO>(`${this.apiUrl}/${id}`);
  }
}
