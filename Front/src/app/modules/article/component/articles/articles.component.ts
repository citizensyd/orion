import {Component, Input} from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";
import {NgStyle} from "@angular/common";
import {ButtonComponent} from "../../../../component/button/classique/button.component";

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    HeaderComponent,
    NgStyle,
    ButtonComponent
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {

}
