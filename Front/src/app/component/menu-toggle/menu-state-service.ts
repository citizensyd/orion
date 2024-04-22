import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MenuStateService {
  private menuOpenSource: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  menuOpen$: Observable<boolean> = this.menuOpenSource.asObservable();

  toggleMenu(): void {
    this.menuOpenSource.next(!this.menuOpenSource.getValue());
  }
}
