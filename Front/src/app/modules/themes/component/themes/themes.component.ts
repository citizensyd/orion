import { Component } from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";

@Component({
  selector: 'app-themes',
  standalone: true,
    imports: [
        HeaderComponent
    ],
  templateUrl: './themes.component.html',
  styleUrl: './themes.component.css'
})
export class ThemesComponent {

}
