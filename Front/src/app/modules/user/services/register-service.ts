import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import {RegisterRequest} from "../interfaces/register-request.interface";
import {RegisterResponse} from "../interfaces/register-response.interface";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private authUrl = environment.authUrl;


  constructor(private http: HttpClient) {
  }

  register(registerRequest: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.authUrl}/register`, registerRequest);
  }

}
