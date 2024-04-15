import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, of, tap, throwError} from 'rxjs';
import { LoginRequest} from '../interfaces/login-request.interface';
import { AuthResponse } from '../interfaces/auth-response.interface';
import { environment } from '../../../../environment/environment';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = environment.authUrl;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.checkTokenValidity());

  constructor(private http: HttpClient) {

  }

  login(loginRequest: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.authUrl}/login`, loginRequest).pipe(
      tap((res: AuthResponse) => {
        if (res && res.token) {
          try {
            localStorage.setItem('access_token', res.token);
          } catch (error) {
            console.error('Error saving to localStorage', error);
          }
          this.isLoggedSubject.next(true);
        }
      }),
      catchError((error) => {
        console.error('Error in login request', error);
        return throwError(() => new Error('Login failed'));
      })
    );
  }
  private checkTokenValidity(): boolean {
    const token = this.getJwtToken();
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      const currentTime = Date.now() / 1000;
      const offsetSeconds = 300;
      return decoded.exp > currentTime + offsetSeconds;
    } catch (error) {
      console.error('Error decoding the token', error);
      return false;
    }
  }

  // Méthode pour récupérer le jeton stocké
  private getJwtToken() {
    return localStorage.getItem('access_token');
  }

  // Méthode pour vérifier si l'utilisateur est connecté
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  // Méthode pour déconnecter l'utilisateur
  logout() {
    localStorage.removeItem('access_token');
    this.isLoggedSubject.next(false);
  }
// Méthode pour rafraîchir la validité du token et retourner un Observable
  refreshTokenValidity(): Observable<boolean> {
    const isTokenValid = this.checkTokenValidity();
    this.isLoggedSubject.next(isTokenValid);
    if (!isTokenValid) {
      localStorage.removeItem('access_token');
    }
    return of(isTokenValid);
  }
}
