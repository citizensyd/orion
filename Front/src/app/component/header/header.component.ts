import { Component } from '@angular/core';
import {AsyncPipe, NgClass} from "@angular/common";
import {AuthService} from "../../modules/user/services/user-services";
import {Observable, Subscription} from "rxjs";
import {MenuComponent} from "../menu/menu.component";
import {MenuToggleComponent} from "../menu-toggle/menu-toggle.component";
import {MenuStateService} from "../menu-toggle/menu-state-service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-header',
  standalone: true,
    imports: [
        NgClass,
        AsyncPipe,
        MenuComponent,
        MenuToggleComponent,
        RouterLink,
    ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  isConnected$: Observable<boolean>;
  menuOpen = false;
  menuSubscription: Subscription = new Subscription();
  constructor(private authService: AuthService, private menuStateService: MenuStateService) {
    this.isConnected$ = this.authService.$isLogged();
  }

  ngOnInit() {
    this.menuSubscription = this.menuStateService.menuOpen$.subscribe((menuOpen: boolean) => {
      this.menuOpen = menuOpen;
    });
  }

}
