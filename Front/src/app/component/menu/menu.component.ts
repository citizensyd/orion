import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {AsyncPipe, NgClass} from "@angular/common";
import {Observable} from "rxjs";
import {AuthService} from "../../modules/user/services/user-services";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    NgClass,
    AsyncPipe,
    RouterLink
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  constructor(public router: Router) {
  }

  isArticlesRouteActive(): boolean {
    return this.router.url === '/articles';
  }
  isThemesRouteActive(): boolean {
    return this.router.url === '/themes';
  }
}
