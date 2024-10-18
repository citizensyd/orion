import {Component, OnDestroy} from '@angular/core';
import {jwtDecode} from "jwt-decode";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ArticleService} from "../../services/article-new.service";
import {ThemeService} from "../../../themes/services/theme-service";
import {ArticleRequest} from "../../interfaces/article-request.interface";
import {ThemeDTO} from "../../../themes/interfaces/theme.interface";
import {NgForOf, NgOptimizedImage} from "@angular/common";
import {HeaderComponent} from "../../../../component/header/header.component";
import {Router, RouterLink} from "@angular/router";
import {Subscription} from "rxjs";
import {tokenDTO} from "../../interfaces/TokenDTO.interface";
import {ArticleResponse} from "../../interfaces/article-response.interface";
import {ErrorHandlingService} from "../../../../services/error-service";

@Component({
  selector: 'app-article-new',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    HeaderComponent,
    RouterLink,
    NgOptimizedImage
  ],
  templateUrl: './article-new.component.html',
  styleUrl: './article-new.component.scss'
})
export class ArticleNewComponent implements OnDestroy{
  articleForm: FormGroup;
  themes: ThemeDTO[] | undefined;
  private themeSubscription: Subscription | undefined;
  private articleSubscription: Subscription | undefined;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService,
    private themeService: ThemeService,
    private errorHandlingService: ErrorHandlingService
  ) {
    this.articleForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      themeId: ['', Validators.required]
    });

    this.loadThemes();
  }

  loadThemes(): void {
    this.themeSubscription = this.themeService.getAllThemes().subscribe({
      next: (themes: ThemeDTO[]) => this.themes = themes,
      error: () => this.errorHandlingService.handleError()
    });
  }

  submit(): void {
    if (this.articleForm.valid) {
      const userId: number|null = this.getUserIdFromToken();
      const articleRequest: ArticleRequest = {
        ...this.articleForm.value,
        userId: userId
      };

      this.articleSubscription = this.articleService.addArticle(articleRequest).subscribe({
        next: (article: ArticleResponse): void => {
          this.router.navigate(['/articles', article.id]);
        },
        error: () => this.errorHandlingService.handleError()
      });
    }
  }

  getUserIdFromToken(): number | null {
    const token: string| null = localStorage.getItem('access_token');
    if (!token) return null;

    try {
      const decodedToken: tokenDTO = jwtDecode(token);
      return decodedToken.userId;
    } catch (error) {
      this.errorHandlingService.handleError()
      return null;
    }
  }
  ngOnDestroy(): void {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
}
