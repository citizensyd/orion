import { Component } from '@angular/core';
import {RegisterRequest} from "../../interfaces/register-request.interface";
import {LogoComponent} from "../../../../component/logo/logo.component";
import {FormsModule} from "@angular/forms";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {RegisterService} from "../../services/register-service";
import {RegisterResponse} from "../../interfaces/register-response.interface";
import {HeaderComponent} from "../header/header.component";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    LogoComponent, FormsModule, ButtonComponent, HeaderComponent, RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  user: RegisterRequest = {
    username: '',
    email: '',
    password: ''
  };

  constructor(private registerService: RegisterService) { }
  onRegisterSubmit() {
    this.registerService.register(this.user).subscribe({
      next: (response) => {
        console.log('User registered', response);
        // Autres logiques
      },
      error: (error) => {
        console.error('Registration error:', error);
        // Gérer l'erreur
      }
    });
  }
}
