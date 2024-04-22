import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from "../modules/user/services/user-services";
import {Observable, of, switchMap, take} from "rxjs";

export const authGuard: CanActivateFn = () => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  return authService.refreshTokenValidity().pipe(
    take(1),
    switchMap((isTokenValid: boolean): Observable<boolean> => {
      if (!isTokenValid) {
        router.navigate(['']);
        return of(false);
      }
      return of(true);
    })
  );
};
