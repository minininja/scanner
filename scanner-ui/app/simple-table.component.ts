import {Component, Input} from '@angular/core';

@Component({
  selector: 'simpleTable',
  template: `<table class="table">
  <tr>
    <th *ngFor="let col of columns">{{col.name}}</th>
  </tr>
  <tr *ngFor="let row of rows">
    <td *ngFor="let col of columns">{{getData(row, col.id)}}</td>
  </tr>
</table>`
})

export class SimpleTableComponent {
  @Input() public rows:Array<any> = [];
  @Input() public columns:any = {};

  public getData(row:any, propertyName:string):string {
    return propertyName.split('.').reduce((prev:any, curr:string) => prev[curr], row);
  }
}
