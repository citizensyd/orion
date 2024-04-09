import {Component, Input} from '@angular/core';
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {ArticleDTO} from "../../interfaces/ArticleDTO.interface";
import {DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [
    TruncatePipe,
    DatePipe,
    RouterLink
  ],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input() article!: ArticleDTO;
}
