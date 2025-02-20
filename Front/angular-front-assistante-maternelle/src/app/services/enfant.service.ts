import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, throwError } from 'rxjs';
import { Enfant } from '../types/enfant.type';

@Injectable({
  providedIn: 'root'
})
export class EnfantService {
  private api_url: string = "http://localhost:8079/enfant";

  constructor(private http: HttpClient) {}

  getAllEnfants(): Observable<Enfant[]> {
    console.log("Requête GET envoyée vers :", this.api_url);
    return this.http.get<Enfant[]>(this.api_url).pipe(
      catchError(error => {
        console.error("Erreur lors de la récupération des enfants :", error);
        alert('Erreur lors de la récupération des enfants : ' + error.message);
        return of([]);
      })
    );
  }


  addEnfant(enfant: Enfant): Observable<Enfant> {
    return this.http.post<Enfant>(`${this.api_url}`, enfant).pipe(
      catchError(error => {
        alert('Erreur lors de l\'ajout de l\'enfant : ' + error.message);
        return of(null as unknown as Enfant);
      })
    );
  }

  getEnfantById(id: string): Observable<Enfant> {
    return this.http.get<Enfant>(`${this.api_url}/${id}`).pipe(
      catchError(error => {
        alert('Erreur lors de la récupération de l\'enfant : ' + error.message);
        return of(null as unknown as Enfant);
      })
    );
  }

  deleteEnfant(id: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.api_url}/${id}`, { observe: 'response' }).pipe(
      catchError(error => {
        return of(null as unknown as HttpResponse<void>);
      })
    );
  }

  updateEnfant(id: string, enfant: Enfant): Observable<Enfant> {
    return this.http.put<Enfant>(`${this.api_url}/${id}`, enfant).pipe(
      catchError(error => {
        alert('Erreur lors de la mise à jour de l\'enfant : ' + error.message);
        return of(null as unknown as Enfant);
      })
    );
  }
}
