import { Component } from '@angular/core';
import {RegisterRequest} from "../../interfaces/register-request.interface";
import {LogoComponent} from "../../../../component/logo/logo.component";
import {FormsModule} from "@angular/forms";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {RegisterService} from "../../services/register-service";
import {RegisterResponse} from "../../interfaces/register-response.interface";
import {HeaderComponent} from "../../../../component/header/header.component";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    LogoComponent, FormsModule, ButtonComponent, HeaderComponent, RouterLink , NgIf
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

  errorMessage: string = '';
  successMessage: string = '';

  constructor(private registerService: RegisterService, private router: Router) { }

  onRegisterSubmit() {
    this.registerService.register(this.user).subscribe({
      next: (response) => {
        this.successMessage = "Enregistrement réussi"
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 3000);
      },
      error: (error) => {
        this.errorMessage = error.error || "Une erreur s'est produite durant l'enregistrement.";
        console.error('Registration error:', error.error);
      }
    });
  }
}
