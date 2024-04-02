import { Component } from '@angular/core';
import {RegisterRequest} from "../../interfaces/register-request.interface";
import {LogoComponent} from "../../../../component/logo/logo.component";
import {FormsModule} from "@angular/forms";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {RegisterService} from "../../services/register-service";
import {RegisterResponse} from "../../interfaces/register-response.interface";
import {HeaderComponent} from "../../../../component/header/header.component";
import {LoginRequest} from "../../interfaces/login-request.interface";
import {AuthService} from "../../services/user-services";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    LogoComponent, FormsModule, ButtonComponent, HeaderComponent, RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  user: LoginRequest = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router) { }
  onLoginSubmit() {
    this.authService.login(this.user).subscribe({
      next: (response) => {
        this.router.navigate(['/articles']);
      },
      error: (error) => {
        console.error('Connection error:', error);
        // Gérer l'erreur
      }
    });
  }
}
