import { Component } from '@angular/core';
import { SimpleTableComponent } from "./simple-table.component";
import { DupeFile } from "./dupe-file";
import { DupeService } from "./dupe.service";

@Component({
  selector: 'my-app',
  directives: [SimpleTableComponent],
  providers: [DupeService],
  template: `<h1>{{title}}</h1>
<hr/>
<simpleTable [columns]="columns" [rows]="dupes"></simpleTable> 
`
})

//  <dupeDetails [dupe]="dupe"></dupeDetails>
export class AppComponent {
  constructor(private dupeService: DupeService) { }

  columns = [{id:'name', name:'Name'},{id:'size',name:'Size'},{id:'path',name:'Path'}];
  title = "Dupe Detector";
  dupes: DupeFile[];

  getDupes() {
    this.dupeService.listDuplicates().then(dupes => this.dupes = dupes);
  }

  ngOnInit() {
    this.getDupes();
  }
}

