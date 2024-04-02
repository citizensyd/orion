import {CanActivateChildFn, CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from "../modules/user/services/user-services";
import {map, take} from "rxjs";

export const unauthGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);
  return authService.$isLogged().pipe(
    take(1),
    map(isLogged => {
      if (isLogged) {
        // Si l'utilisateur est déjà connecté, redirige vers la page 'article' et empêche l'accès à la route actuelle
        router.navigate(['articles']).then(success => {
          if (!success) {
            console.log('La navigation vers article a échoué!');
          }
        }).catch(error => {
          console.error('Erreur lors de la navigation:', error);
        });
        console.log('unauthGuard to article')
        return false;
      }
      // Permet l'accès si l'utilisateur n'est pas connecté
      return true;
    })
  );
};
