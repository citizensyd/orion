import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, of, tap, throwError} from 'rxjs';
import { LoginRequest} from '../interfaces/login-request.interface';
import { AuthResponse } from '../interfaces/auth-response.interface';
import { environment } from '../../../../environments/environment';
import { jwtDecode } from "jwt-decode";
import {ErrorHandlingService} from "../../../services/error-service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl: string = environment.authUrl;
  private isLoggedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.checkTokenValidity());

  constructor(private http: HttpClient, private errorHandlingService: ErrorHandlingService) {

  }

  login(loginRequest: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.authUrl}/login`, loginRequest).pipe(
      tap((res: AuthResponse): void => {
        if (res && res.token) {
          localStorage.setItem('access_token', res.token);
          this.isLoggedSubject.next(true);
        }
      }),
      catchError(() => {
        return throwError(() => new Error('Login failed'));
      })
    );
  }
  private checkTokenValidity(): boolean {
    const token: string|null = this.getJwtToken();
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      const currentTime: number = Date.now() / 1000;
      const offsetSeconds: number = 300;
      return decoded.exp > currentTime + offsetSeconds;
    } catch (error) {
      this.errorHandlingService.handleError();
      return false;
    }
  }

  // Méthode pour récupérer le jeton stocké
  private getJwtToken(): string|null {
    return localStorage.getItem('access_token');
  }

  // Méthode pour vérifier si l'utilisateur est connecté
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  // Méthode pour déconnecter l'utilisateur
  logout(): void {
    localStorage.removeItem('access_token');
    this.isLoggedSubject.next(false);
  }
// Méthode pour rafraîchir la validité du token et retourner un Observable
  refreshTokenValidity(): Observable<boolean> {
    const isTokenValid: boolean = this.checkTokenValidity();
    this.isLoggedSubject.next(isTokenValid);
    if (!isTokenValid) {
      localStorage.removeItem('access_token');
    }
    return of(isTokenValid);
  }
}
