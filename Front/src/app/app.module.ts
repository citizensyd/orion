import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {RouterOutlet} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
  declarations: [
   ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    RouterOutlet
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ]
})
export class AppModule { }
