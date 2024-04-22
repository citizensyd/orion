import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentResponse} from "../../interfaces/comment-response.interface";
import {CommentService} from "../../services/comment.service";
import {CommentRequest} from "../../interfaces/comment-request.interface";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    NgOptimizedImage
  ],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.css'
})
export class CommentComponent implements OnInit, OnDestroy {
  @Input() articleId!: number;
  comments: CommentResponse[] = [];
  newCommentContent: string = '';
  private commentSubscriptions: Subscription;

  constructor(private commentService: CommentService) {
    this.commentSubscriptions = new Subscription();
  }

  ngOnInit(): void {
    if (this.articleId) {
      this.loadComments(this.articleId);
    }
  }

  private loadComments(articleId: number): void {
    this.commentSubscriptions.add(this.commentService.getCommentsByArticleId(articleId).subscribe({
      next: (comments: CommentResponse[]) => this.comments = comments,
      error: (error) => console.error('Failed to load comments:', error)
    }));
  }

  addComment(): void {
    if (!this.newCommentContent.trim()) {
      console.error('Comment content is empty');
      return;
    }
    const commentRequest: CommentRequest = {
      content: this.newCommentContent,
      articleId: this.articleId,
    };
    this.commentSubscriptions.add(this.commentService.addCommentToArticle(this.articleId, commentRequest).subscribe({
      next: (comment: CommentResponse): void => {
        this.comments.push(comment);
        this.newCommentContent = '';
      },
      error: (error) => console.error('Failed to add comment:', error)
    }));
  }
  ngOnDestroy(): void {
    this.commentSubscriptions.unsubscribe();
  }
}
