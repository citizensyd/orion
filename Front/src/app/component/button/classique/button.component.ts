import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [],
  templateUrl: './button.component.html',
  styleUrl: './button.component.css'
})
export class ButtonComponent implements OnInit{
  @Input() buttonText: string = '';
  @Output() click = new EventEmitter<void>();
  constructor() { }
  handleClick() {
    this.click.emit();
  }
  ngOnInit(): void {
  }
}
