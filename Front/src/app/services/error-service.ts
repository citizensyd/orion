import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {
  constructor(private router: Router) {}

  handleError() {
    console.error('An error occurred:');
    this.router.navigate(['/error']);
  }
}
