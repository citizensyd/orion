import {CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from "../modules/user/services/user-services";

export const unauthGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLogged) {
    router.navigate(['articles']);
    return false;
  }
  return true;
};
