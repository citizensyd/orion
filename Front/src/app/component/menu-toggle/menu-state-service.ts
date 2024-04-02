import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MenuStateService {
  private menuOpenSource = new BehaviorSubject<boolean>(false);

  menuOpen$ = this.menuOpenSource.asObservable();

  toggleMenu() {
    this.menuOpenSource.next(!this.menuOpenSource.getValue());
  }
}
