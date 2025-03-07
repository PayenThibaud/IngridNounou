import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;   // Déclaration du formulaire
  errorMessage = '';      // Message d'erreur en cas de mauvaise connexion
  isEditMode = false;     // Détermine si c'est en mode édition
  userName: string | null = null; // Variable pour stocker le nom de l'utilisateur

  constructor(
    private authService: AuthService,    // Service d'authentification
    private router: Router,              // Service de navigation
    private fb: FormBuilder              // FormBuilder pour créer le formulaire
  ) {}

  ngOnInit(): void {
    // Création du formulaire réactif avec des validations
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Validation de l'email
      password: ['', [Validators.required]]                // Validation du mot de passe
    });

    // Souscription pour récupérer le nom de l'utilisateur après la connexion
    this.authService.getUserName().subscribe(name => {
      this.userName = name;  // Mise à jour du nom de l'utilisateur
    });
  }

  // Méthode pour se connecter
  login() {
    if (this.loginForm.invalid) {
      return; // Si le formulaire est invalide, on ne fait rien
    }

    const { email, password } = this.loginForm.value;  // Récupération des valeurs du formulaire

    // Appel du service pour se connecter
    this.authService.login(email, password).subscribe(success => {
      if (success) {
        this.router.navigate(['/profile']);  // Si la connexion est réussie, rediriger vers le profil
      } else {
        this.errorMessage = 'Email ou mot de passe incorrect.'; // Sinon, afficher un message d'erreur
      }
    });
  }
}
