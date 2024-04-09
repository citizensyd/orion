import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import { ArticleService } from '../../services/article-detail.service';
import { ArticleDTO } from '../../interfaces/ArticleDTO.interface';
import {HeaderComponent} from "../../../../component/header/header.component";
import {DatePipe, NgIf} from "@angular/common";
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {CommentComponent} from "../comment/comment.component";

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
export class ArticleDetailComponent implements OnInit {
  article: ArticleDTO | null = null;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadArticle(+id);
      } else {
        console.error('Article ID is missing in the route parameters.');
      }
    });
  }

  private loadArticle(articleId: number): void {
    this.articleService.getArticleById(articleId).subscribe({
      next: (article: any) => {
        this.article = article;
      },
      error: (error: any) => {
        console.error('Failed to load the article:', error);
        this.article = null;
      }
    });
  }
}
