import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UpdateRequest } from '../interfaces/update-request.interface';
import { UpdateResponse } from "../interfaces/update-response.interface";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProfilService {

  constructor(private http: HttpClient) {}

  private apiUrl: string = `${environment.userUrl}`;

  updateUserProfile(data: UpdateRequest): Observable<UpdateResponse> {
    return this.http.put<UpdateResponse>(`${this.apiUrl}`, data);
  }
}
