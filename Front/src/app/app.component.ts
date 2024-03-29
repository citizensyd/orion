import {Component, Input, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {Observable} from 'rxjs';
import { AuthService } from './modules/user/services/user-services';
import {AsyncPipe, NgIf} from "@angular/common";
import {ButtonComponent} from "./component/button/classique/button.component";
import {LogoComponent} from "./component/logo/logo.component";
import {LoginComponent} from "./modules/user/component/login/login.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, ButtonComponent, NgIf, LogoComponent, RouterLink],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  @Input() routerLink: string | any[] | undefined;

  clickedLogin: boolean = false;
  clickedRegister: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {

  }

  navigateToLogin() {
    this.clickedLogin = true;
    this.clickedRegister = false;
    this.router.navigate(['/login']);
  }

  navigateToRegister() {
    this.clickedRegister = true;
    this.clickedLogin = false;
    this.router.navigate(['/register']);
  }

}
