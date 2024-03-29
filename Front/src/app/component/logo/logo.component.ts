import {Component, Input} from '@angular/core';
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-logo',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './logo.component.html',
  styleUrl: './logo.component.css'
})
export class LogoComponent {
  @Input() size: 'small' | 'medium' | 'large' | undefined;
}
