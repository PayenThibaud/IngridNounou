import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, throwError } from 'rxjs';
import { Planning } from '../types/planning.type';

@Injectable({
  providedIn: 'root'
})
export class PlanningService {
  private api_url: string = "http://localhost:8079/planning";

  constructor(private http: HttpClient) {}

  getAllPlannings(): Observable<Planning[]> {
    console.log("Requête GET envoyée vers :", this.api_url);
    return this.http.get<Planning[]>(this.api_url).pipe(
      catchError(error => {
        console.error("Erreur lors de la récupération des plannings :", error);
        alert('Erreur lors de la récupération des plannings : ' + error.message);
        return of([]);
      })
    );
  }

  addPlanning(planning: Planning): Observable<Planning> {
    return this.http.post<Planning>(`${this.api_url}`, planning).pipe(
      catchError(error => {
        alert('Erreur lors de l\'ajout du planning : ' + error.message);
        return of(null as unknown as Planning);
      })
    );
  }

  getPlanningById(id: string): Observable<Planning> {
    return this.http.get<Planning>(`${this.api_url}/${id}`).pipe(
      catchError(error => {
        alert('Erreur lors de la récupération du planning : ' + error.message);
        return of(null as unknown as Planning);
      })
    );
  }

  deletePlanning(id: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.api_url}/${id}`, { observe: 'response' }).pipe(
      catchError(error => {
        alert('Erreur lors de la suppression du planning : ' + error.message);
        return of(null as unknown as HttpResponse<void>);
      })
    );
  }

  updatePlanning(id: string, planning: Planning): Observable<Planning> {
    return this.http.put<Planning>(`${this.api_url}/${id}`, planning).pipe(
      catchError(error => {
        alert('Erreur lors de la mise à jour du planning : ' + error.message);
        return of(null as unknown as Planning);
      })
    );
  }
}
