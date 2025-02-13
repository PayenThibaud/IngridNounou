import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EnfantService {
  private apiUrl = 'http://localhost:8082/enfant';

  constructor(private http: HttpClient) {}

  getEnfants(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}

