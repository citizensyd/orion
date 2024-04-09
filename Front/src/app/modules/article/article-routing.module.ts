import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ArticlesComponent} from "./component/articles/articles.component";
import {ArticleDetailComponent} from "./component/article-detail/article-detail.component";


const routes: Routes = [
  { title: 'Article', path: '', component: ArticlesComponent },
  { title: 'Article Detail', path: ':id', component: ArticleDetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticleRoutingModule { }
