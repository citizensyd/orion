import { Component } from '@angular/core';
import {jwtDecode} from "jwt-decode";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ArticleService} from "../../services/article-new.service";
import {ThemeService} from "../../../themes/services/theme-service";
import {AuthService} from "../../../user/services/user-services";
import {ArticleRequest} from "../../interfaces/article-request.interface";
import {ThemeDTO} from "../../../themes/interfaces/theme.interface";
import {NgForOf, NgOptimizedImage} from "@angular/common";
import {HeaderComponent} from "../../../../component/header/header.component";
import {RouterLink} from "@angular/router";

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
export class ArticleNewComponent {
  articleForm: FormGroup;
  themes: ThemeDTO[] | undefined;

  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private themeService: ThemeService,
  ) {
    this.articleForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      themeId: ['', Validators.required]
    });

    this.loadThemes();
  }

  loadThemes(): void {
    this.themeService.getAllThemes().subscribe({
      next: (themes) => this.themes = themes,
      error: (error) => console.error('Erreur lors du chargement des thèmes', error)
    });
  }

  submit(): void {
    if (this.articleForm.valid) {
      const userId = this.getUserIdFromToken();
      const articleRequest: ArticleRequest = {
        ...this.articleForm.value,
        userId: userId
      };

      this.articleService.addArticle(articleRequest).subscribe({
        next: (article) => console.log('Article ajouté avec succès', article),
        error: (error) => console.error('Erreur lors de l’ajout de l’article', error)
      });
    }
  }
  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('access_token');
    if (!token) return null;

    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.userId;
    } catch (error) {
      console.error('Erreur de décodage du token:', error);
      return null;
    }
  }
}
