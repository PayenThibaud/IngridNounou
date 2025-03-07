import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8079/api/auth';  // URL de l'API pour l'authentification
  private userSubject = new BehaviorSubject<string | null>(localStorage.getItem('token'));  // Suivi du token utilisateur
  private userNameSubject = new BehaviorSubject<string | null>(localStorage.getItem('username')); // Nouveau sujet pour le nom


  constructor(private http: HttpClient, private router: Router) {}

  // Méthode de connexion
  login(email: string, password: string): Observable<boolean> {
    const body = {
      email: email,
      password: password
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',  // Spécification du type de contenu JSON
    });

    // Requête POST pour la connexion
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, body, { headers })
      .pipe(
        map(response => {
          if (response.token && response.token !== 'NotFound') {
            localStorage.setItem('token', response.token);  // Si succès, on sauvegarde le token
            this.userSubject.next(response.token);          // Met à jour le sujet avec le token
            return true;
          }
          return false;  // Sinon, l'authentification a échoué
        })
      );
  }

  getUserName() {
    return this.userNameSubject.asObservable();  // Retourne un observable pour récupérer le nom
  }

  // Méthode de déconnexion
  logout() {
    localStorage.removeItem('token');  // Retirer le token du localStorage
    this.userSubject.next(null);       // Mettre à jour le sujet de l'utilisateur
    this.router.navigate(['/login']);  // Rediriger vers la page de connexion
  }

  // Vérifie si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');  // Retourne true si un token est présent
  }
}
