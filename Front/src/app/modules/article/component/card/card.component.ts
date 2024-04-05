import {Component, Input} from '@angular/core';
import {TruncatePipe} from "../../../../pipes/truncate.pipe";
import {ArticleDTO} from "../../interfaces/ArticleDTO.interface";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [
    TruncatePipe,
    DatePipe
  ],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input() article!: ArticleDTO;
}
