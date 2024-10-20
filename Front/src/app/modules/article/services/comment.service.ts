// comment.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommentRequest } from '../interfaces/comment-request.interface';
import { CommentResponse } from '../interfaces/comment-response.interface';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private commentUrl: string = `${environment.commentUrl}`;

  constructor(private http: HttpClient) { }

  getCommentsByArticleId(articleId: number): Observable<CommentResponse[]> {
    const url: string = `${this.commentUrl}/${articleId}/comments`;
    return this.http.get<CommentResponse[]>(url);
  }

  addCommentToArticle(articleId: number, commentRequest: CommentRequest): Observable<CommentResponse> {
    const url: string = `${this.commentUrl}/${articleId}/comments`;
    return this.http.post<CommentResponse>(url, commentRequest);
  }
}
