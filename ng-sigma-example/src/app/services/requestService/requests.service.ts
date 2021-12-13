import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { DataNode } from 'src/app/data/datanode';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private url = "http://localhost:8080"
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    })
  };
  constructor(private http: HttpClient) {

  }

  public startService(): Observable<Object> {
    return this.http.post(this.url + "/startAsync", JSON.stringify({}), this.httpOptions)
  }

  public getTree(id: string): Observable<Map<String, DataNode>> {
    return this.http.post<Map<String, DataNode>>(this.url + "/getResult/" + id, {}, this.httpOptions)
  }


}