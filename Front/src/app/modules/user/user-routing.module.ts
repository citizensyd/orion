import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import {AppComponent} from "../../app.component";
import {ArticlesComponent} from "../article/component/articles/articles.component";
import {ProfilComponent} from "../profil/component/profil/profil.component";


const routes: Routes = [
  { title: 'Home', path: '', component: AppComponent },
  { title: 'Login', path: 'login', component: LoginComponent },
  { title: 'Register', path: 'register', component: RegisterComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
