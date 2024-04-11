// Importez les modules nécessaires et le UserService
import { Component } from '@angular/core';
import { HeaderComponent } from "../../../../component/header/header.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { UpdateRequest } from "../../interfaces/update-request.interface";
import { ProfilService } from "../../services/profil-update.interface";
import {Router} from "@angular/router";
import {AuthService} from "../../../user/services/user-services";
import {ThemesComponent} from "../themes/themes.component";

@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [
    HeaderComponent,
    ReactiveFormsModule,
    FormsModule,
    ThemesComponent
  ],
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent {
  user: UpdateRequest = {
    email: '',
    username: ''
  };

  constructor(private profilService: ProfilService,private router: Router,private authService: AuthService,) {}

  // Méthode pour soumettre le profil
  submitProfile(): void {
    this.profilService.updateUserProfile(this.user).subscribe({
      next: (response: any) => {
        console.log('Mise à jour réussie', response);
      },
      error: (error: any) => {
        console.error('Erreur lors de la mise à jour du profil', error);
      }
    });
  }
  // Méthode de déconnexion
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']).then(() => {
    });
  }
}
