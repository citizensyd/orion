import {Component, Input} from '@angular/core';
import {HeaderComponent} from "../../../../component/header/header.component";
import {NgStyle} from "@angular/common";

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    HeaderComponent,
    NgStyle
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {

}
