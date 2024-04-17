import { Component, OnInit, OnDestroy } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import { NgClass } from "@angular/common";
import { MenuStateService } from "./menu-state-service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-menu-toggle',
  standalone: true,
  imports: [
    NgClass,
    RouterLink
  ],
  templateUrl: './menu-toggle.component.html',
  styleUrls: ['./menu-toggle.component.css']
})
export class MenuToggleComponent implements OnInit, OnDestroy {
  menuOpen = false;
  menuSubscription: Subscription;

  constructor(private router: Router, private menuStateService: MenuStateService) {
    this.menuSubscription = new Subscription()
  }

  ngOnInit() {
    this.menuSubscription.add(this.menuStateService.menuOpen$.subscribe((menuOpen: boolean) => {
      this.menuOpen = menuOpen;
    }));
  }

  ngOnDestroy() {
    this.menuSubscription.unsubscribe();
  }

  isArticlesRouteActive(): boolean {
    return this.router.url === '/articles';
  }

  isThemesRouteActive(): boolean {
    return this.router.url === '/themes';
  }

  toggleMenu() {
    this.menuStateService.toggleMenu();
  }
}
