import {CanActivateChildFn, CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from "../modules/user/services/user-services";
import {map, take} from "rxjs";

export const unauthGuard: CanActivateFn = () => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);
  return authService.$isLogged().pipe(
    take(1),
    map(isLogged => {
      if (isLogged) {
        // Si l'utilisateur est déjà connecté, redirige vers la page 'article' et empêche l'accès à la route actuelle
        router.navigate(['articles']).then(success => {
        }).catch(error => {
          return false;
        });
        return false;
      }
      // Permet l'accès si l'utilisateur n'est pas connecté
      return true;
    })
  );
};
