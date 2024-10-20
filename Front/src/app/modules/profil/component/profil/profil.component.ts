// Importez les modules nécessaires et le UserService
import {Component, OnDestroy} from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UpdateRequest} from "../../interfaces/update-request.interface";
import {ProfilService} from "../../services/profil-update.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../user/services/user-services";
import {ThemesProfilComponent} from "../themes/themes-profil.component";
import {UpdateResponse} from "../../interfaces/update-response.interface";
import {Subscription} from "rxjs";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [
    HeaderComponent,
    ReactiveFormsModule,
    FormsModule,
    ThemesProfilComponent,
    NgIf
  ],
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnDestroy {
  errorMessage: string = '';
  successMessage: string = '';
  user: UpdateRequest = {
    email: '',
    username: ''
  };
  private subscriptions: Subscription = new Subscription();

  constructor(private profilService: ProfilService, private router: Router, private authService: AuthService,) {
  }

  // Méthode pour soumettre le profil
  submitProfile(): void {
    this.subscriptions.add(this.profilService.updateUserProfile(this.user).subscribe({
      next: (): void => {
        this.successMessage = 'Mise à jour réussie';
      },
      error: (): void => {
        this.errorMessage = 'Erreur lors de la mise à jour du profil';
      }
    }));
  }

  // Méthode de déconnexion
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']).then(() => {
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
