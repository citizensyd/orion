import {CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from "../modules/user/services/user-services";
import {map, take} from "rxjs";

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

    return authService.$isLogged().pipe(
      take(1),
      map(isLogged => {
        if (!isLogged) {
          console.log('authGuard to home')
          router.navigate(['']);
          return false;
        }
        console.log('authGuard true')
        return true;
      })
    );
};
