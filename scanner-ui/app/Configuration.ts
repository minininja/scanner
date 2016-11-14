import { Injectable } from '@angular/core';

@Injectable()
export class Configuration {
  public elasticSearchHost: string = "http://localhost:9200/";
}
