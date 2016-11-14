import { Injectable } from '@angular/core';
import {DUPES} from "./mock-dupes";
import { Http, Response, Headers } from '@angular/http';
import {Configuration} from "./Configuration";

@Injectable()
export class DupeService {
  endPoint: string;
  headers: Headers;

  constructor(private http: Http, private configuration: Configuration) {
    this.endPoint = configuration.elasticSearchHost + "_search";

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  duplidateJson(block: string, hash:string) {
    var z = "hash." + block;
    return JSON.stringify({"query":{"bool":{"must":[{"range":{z:{"eq":hash}}}]}},"size":0,"aggs":{"dups":{"terms":{"field":z}}}});
  }

  hashJson(block: string, hash: string) {
    // todo
  }

  listDuplicates(block: string, hash:string) {
    var result = this.http.post(this.endPoint, this.blockJson(block, hash));

    return Promise.resolve(DUPES);
  }
}

