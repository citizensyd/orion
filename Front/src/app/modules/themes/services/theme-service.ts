import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environment/environment';
import {ThemeDTO} from "../interfaces/theme.interface";
import {Observable} from "rxjs";
import {SubscriptionRequest} from "../interfaces/subscription-request.interface";
import { jwtDecode } from 'jwt-decode';
import {SubscriptionDTO} from "../../article/interfaces/subscription.interface";


@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private apiUrl = `${environment.backendUrl}`;

  constructor(private http: HttpClient) { }

  getAllThemes(): Observable<ThemeDTO[]> {
    return this.http.get<ThemeDTO[]>(this.apiUrl);
  }
  getSubscriptionsByUser(): Observable<SubscriptionDTO[]> {
    const url = `${this.apiUrl}/user`;
    return this.http.get<SubscriptionDTO[]>(url);
  }
  subscribeToTheme(subscriptionRequest: SubscriptionRequest): Observable<object> {
    return this.http.post(`${this.apiUrl}`, subscriptionRequest);
  }
  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('access_token');
    if (!token) return null;

    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.userId;
    } catch (error) {
      console.error('Erreur de décodage du token:', error);
      return null;
    }
  }
}
