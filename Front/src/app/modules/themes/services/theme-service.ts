import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environment/environment';
import {ThemeDTO} from "../interfaces/theme.interface";
import {Observable} from "rxjs";
import {SubscriptionRequest} from "../interfaces/subscription-request.interface";
import {jwtDecode} from 'jwt-decode';
import {SubscriptionDTO} from "../../article/interfaces/subscription.interface";
import {ErrorHandlingService} from "../../../services/error-service";


@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private apiUrl: string = `${environment.backendUrl}`;

  constructor(private http: HttpClient, private errorHandlingService: ErrorHandlingService) {

  }

  getAllThemes(): Observable<ThemeDTO[]> {
    return this.http.get<ThemeDTO[]>(this.apiUrl);
  }

  getSubscriptionsByUser(): Observable<SubscriptionDTO[]> {
    const url: string = `${this.apiUrl}/user`;
    return this.http.get<SubscriptionDTO[]>(url);
  }

  subscribeToTheme(subscriptionRequest: SubscriptionRequest): Observable<object> {
    return this.http.post(`${this.apiUrl}`, subscriptionRequest);
  }

  unSubscribeToTheme(subscriptionRequest: SubscriptionRequest): Observable<object> {
    return this.http.delete(`${this.apiUrl}`, {body: subscriptionRequest});
  }

  getUserIdFromToken(): number | null {
    const token: string | null = localStorage.getItem('access_token');
    if (!token) return null;

    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.userId;
    } catch (error) {
      this.errorHandlingService.handleError();
      return null;
    }
  }
}
