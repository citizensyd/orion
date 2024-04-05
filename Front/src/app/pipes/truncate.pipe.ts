import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate',
  standalone: true
})
export class TruncatePipe implements PipeTransform {

  transform(value: string | undefined, limit: number): string {
    if (!value) return '';
    const trail = '...';

    if (value.length <= limit) {
      return value;
    }

    let end = value.substring(0, limit).lastIndexOf(' ');
    end = end === -1 ? limit : end;

    return `${value.substring(0, end)}${trail}`;
  }
}
