import {Component, Input, OnInit} from '@angular/core';
import {NavigationEnd, Router, RouterLink, RouterOutlet} from '@angular/router';
import {filter, Observable} from 'rxjs';
import { AuthService } from './modules/user/services/user-services';
import {AsyncPipe, NgIf} from "@angular/common";
import {ButtonComponent} from "./component/button/classique/button.component";
import {LogoComponent} from "./component/logo/logo.component";
import {HeaderComponent} from "./component/header/header.component";
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, ButtonComponent, NgIf, LogoComponent, RouterLink, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  @Input() routerLink: string | any[] | undefined;

  showHeader: boolean = false;
  clickedLogin: boolean = false;
  clickedRegister: boolean = false;
  isLogged$: Observable<boolean>;
  public assetPath: string = environment.assetPath;


  constructor(private router: Router, private authService: AuthService) {
    this.isLogged$ = this.authService.$isLogged();
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      this.showHeader = event.urlAfterRedirects !== '/';
    });
  }

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
