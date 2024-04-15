import {CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from "../modules/user/services/user-services";
import {map, of, switchMap, take} from "rxjs";

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.refreshTokenValidity().pipe(
    take(1),
    switchMap((isTokenValid) => {
      if (!isTokenValid) {
        console.log("false")
        router.navigate(['']);
        return of(false);
      }
      console.log("true")
      return of(true);
    })
  );
};
