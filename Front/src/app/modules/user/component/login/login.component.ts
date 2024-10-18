import {Component, OnDestroy} from '@angular/core';
import {LogoComponent} from "../../../../component/logo/logo.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ButtonComponent} from "../../../../component/button/classique/button.component";
import {HeaderComponent} from "../../../../component/header/header.component";
import {AuthService} from "../../services/user-services";
import {Router, RouterLink} from "@angular/router";
import {Subscription} from "rxjs";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    LogoComponent, FormsModule, ButtonComponent, HeaderComponent, RouterLink, ReactiveFormsModule, NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnDestroy {

  loginForm: FormGroup;
  errorMessage: string = '';
  private subscriptions: Subscription = new Subscription();

  constructor(private fb: FormBuilder,private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }
  onLoginSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (): void => {
          this.router.navigate(['/articles']);
        },
        error: (error: string):void => {
          this.errorMessage = error || "Une erreur s'est produite lors de la connexion";
        }
      });
    }
  }
  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
