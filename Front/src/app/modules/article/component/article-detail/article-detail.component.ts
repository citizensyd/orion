import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {ArticleService} from '../../services/article-detail.service';
import {ArticleDTO} from '../../interfaces/ArticleDTO.interface';
import {HeaderComponent} from "../../../../component/header/header.component";
import {DatePipe, NgIf} from "@angular/common";
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {CommentComponent} from "../comment/comment.component";
import {Subscription} from "rxjs";
import {ErrorHandlingService} from "../../../../services/error-service";

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterLink,
    DatePipe,
    TruncatePipe,
    CommentComponent,
    NgIf,
  ],
  styleUrls: ['./article-detail.component.css']
})
export class ArticleDetailComponent implements OnInit, OnDestroy {
  article: ArticleDTO | null = null;
  private paramMapSubscription: Subscription | undefined;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private errorHandlingService: ErrorHandlingService
  ) {
  }

  ngOnInit(): void {
    this.paramMapSubscription = this.route.paramMap.subscribe(params => {
      const id: string | null = params.get('id');
      if (id) {
        this.loadArticle(+id);
      }
    });
  }

  private loadArticle(articleId: number): void {
    this.articleService.getArticleById(articleId).subscribe({
      next: (article: ArticleDTO): void => {
        this.article = article;
      },
      error: (): void => {
        this.errorHandlingService.handleError();
        this.article = null;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.paramMapSubscription) {
      this.paramMapSubscription.unsubscribe();
    }
  }
}
