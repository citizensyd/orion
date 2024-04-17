import {Component, OnDestroy} from '@angular/core';
import {RegisterRequest} from "../../interfaces/register-request.interface";
import {LogoComponent} from "../../../../component/logo/logo.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {RegisterService} from "../../services/register-service";
import {RegisterResponse} from "../../interfaces/register-response.interface";
import {HeaderComponent} from "../../../../component/header/header.component";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    LogoComponent, FormsModule, ButtonComponent, HeaderComponent, RouterLink, NgIf, ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnDestroy {
  registerForm: FormGroup;
  sub: Subscription = new Subscription();


  errorMessage: string = '';
  successMessage: string = '';

  constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onRegisterSubmit() {
    if (this.registerForm.valid) {
      this.sub.add(this.registerService.register(this.registerForm.value).subscribe({
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
      }));
    }
  }
  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
