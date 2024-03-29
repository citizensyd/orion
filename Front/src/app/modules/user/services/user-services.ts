import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, tap, throwError} from 'rxjs';
import { LoginRequest} from '../interfaces/login-request.interface';
import { AuthResponse } from '../interfaces/auth-response.interface';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = environment.authUrl;
  public isLogged: boolean = false;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  constructor(private http: HttpClient) {
    this.isLogged = !!this.getJwtToken();
    this.isLoggedSubject.next(this.isLogged);
  }

  login(loginRequest: LoginRequest): Observable<AuthResponse> {
    console.log('Attempting login', loginRequest);
    return this.http.post<AuthResponse>(`${this.authUrl}/login`, loginRequest).pipe(
      tap((res: AuthResponse) => {
        console.log('Response received', res);
        if (res && res.token) {
          try {
            localStorage.setItem('access_token', res.token);
            console.log('Token stored in local storage');
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
    this.isLogged = false;
    this.isLoggedSubject.next(this.isLogged);
  }

}
