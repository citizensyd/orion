import {Component, OnDestroy, OnInit} from '@angular/core';
import {AsyncPipe, NgClass} from "@angular/common";
import {AuthService} from "../../modules/user/services/user-services";
import {Observable, Subscription} from "rxjs";
import {MenuComponent} from "../menu/menu.component";
import {MenuToggleComponent} from "../menu-toggle/menu-toggle.component";
import {MenuStateService} from "../menu-toggle/menu-state-service";
import {RouterLink} from "@angular/router";
import { environment } from '../../../environments/environment';

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
export class HeaderComponent implements OnInit, OnDestroy{
  isConnected$: Observable<boolean>;
  menuOpen: boolean = false;
  menuSubscription: Subscription;
  public assetPath: string = environment.assetPath;
  constructor(private authService: AuthService, private menuStateService: MenuStateService) {
    this.isConnected$ = this.authService.$isLogged();
    this.menuSubscription = new Subscription();
  }

  ngOnInit(): void {
    this.menuSubscription = this.menuStateService.menuOpen$.subscribe((menuOpen: boolean) => {
      this.menuOpen = menuOpen;
    });
  }
  ngOnDestroy(): void {
    this.menuSubscription.unsubscribe();
  }

}
