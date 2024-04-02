import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { NgClass } from "@angular/common";
import { MenuStateService } from "./menu-state-service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-menu-toggle',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './menu-toggle.component.html',
  styleUrls: ['./menu-toggle.component.css']
})
export class MenuToggleComponent implements OnInit {
  menuOpen = false;
  menuSubscription: Subscription = new Subscription();

  constructor(public router: Router, private menuStateService: MenuStateService) {}

  ngOnInit() {
    this.menuSubscription = this.menuStateService.menuOpen$.subscribe((menuOpen: boolean) => {
      this.menuOpen = menuOpen;
    });
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
