import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './component/not-found/not-found.component';
import { authGuard } from './guards/auth.guard';
import { unauthGuard } from './guards/unauth.guard';

export const routes: Routes = [
  {
    path: '',
    canActivate: [unauthGuard],
    loadChildren: () => import('./modules/user/user.module').then(m => m.UserModule)
 },
  {
    path: 'profil',
    canActivate: [authGuard],
    loadChildren: () => import('./modules/profil/profil.module').then(m => m.ProfilModule)
  },
  {
    path: 'themes',
    canActivate: [authGuard],
    loadChildren: () => import('./modules/themes/theme.module').then(m => m.ThemeModule)
  },
  {
    path: 'articles',
    canActivate: [authGuard],
    loadChildren: () => import('./modules/article/article.module').then(m => m.ArticleModule)
  },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
