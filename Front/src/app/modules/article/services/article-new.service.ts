import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import {ArticleResponse} from "../interfaces/article-response.interface";
import {ArticleRequest} from "../interfaces/article-request.interface";
import {environment} from "../../../../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl: string = `${environment.articleUrl}`;

  constructor(private http: HttpClient) { }

  addArticle(articleRequest: ArticleRequest): Observable<ArticleResponse> {
    return this.http.post<ArticleResponse>(this.apiUrl, articleRequest).pipe(
      catchError((error: any) => throwError(() => new Error(`error in posting new article: ${error}`)))
    );
  }
}
