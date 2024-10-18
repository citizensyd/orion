import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Injectable } from "@angular/core";
import {Observable} from "rxjs";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {

  public intercept(request: HttpRequest<any>, next: HttpHandler):Observable<HttpEvent<any>> {
    const token: string|null = localStorage.getItem('access_token');
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        },
      });
    }
    return next.handle(request);
  }
}
