import {Component, OnDestroy} from '@angular/core';
import {LogoComponent} from "../../../../component/logo/logo.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {HeaderComponent} from "../../../../component/header/header.component";
import {AuthService} from "../../services/user-services";
import {Router, RouterLink} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    LogoComponent, FormsModule, ButtonComponent, HeaderComponent, RouterLink, ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnDestroy {

  loginForm: FormGroup;
  private subscriptions = new Subscription();

  constructor(private fb: FormBuilder,private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }
  onLoginSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
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
  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}
