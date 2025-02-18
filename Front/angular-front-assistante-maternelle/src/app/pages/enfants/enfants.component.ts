import {Component, OnInit} from '@angular/core';
import {EnfantService} from '../../services/enfant.service';
import {Enfant} from '../../types/enfant.type';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-enfants',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './enfants.component.html',
  styleUrls: ['./enfants.component.css']
})

export class EnfantsComponent implements OnInit {
  enfants: Enfant[] = [];
  enfantForm: FormGroup;
  isEditMode = false;
  enfantIdToUpdate: string | null = null;

  constructor(private enfantService: EnfantService, private fb: FormBuilder, private router: Router) {
    this.enfantForm = this.fb.group({
      prenom: [''],
      nom: [''],
      dateNaissance: [''],
      fille: [false]
    });
  }

  ngOnInit(): void {
    this.getAllEnfants();
  }

  getAllEnfants(): void {
    this.enfantService.getAllEnfants().subscribe(data => {
      this.enfants = data;
    });
  }

  addEnfant(): void {
    if (this.enfantForm.invalid) {
      alert("Veuillez remplir tous les champs obligatoires !");
      return;
    }
    const newEnfant: Enfant = this.enfantForm.value;
    this.enfantService.addEnfant(newEnfant).subscribe(() => {
      alert('Enfant ajouté avec succès');
      this.enfantForm.reset();
      this.getAllEnfants();
    });
  }

  deleteEnfant(id: string): void {
    if (confirm("Êtes-vous sûr de vouloir supprimer cet enfant ?")) {
      this.enfantService.deleteEnfant(id).subscribe({
        next: () => {
          alert('Enfant supprimé avec succès');
          this.getAllEnfants();
        },
        error: err => {
          console.error('Erreur lors de la suppression de l\'enfant:', err);
        }
      });
    }
  }

  editEnfant(enfant: Enfant): void {
    this.isEditMode = true;
    this.enfantIdToUpdate = enfant.idEnfant;
    this.enfantForm.patchValue({
      prenom: enfant.prenom,
      nom: enfant.nom,
      dateNaissance: enfant.dateNaissance,
      fille: enfant.fille
    });
  }

  updateEnfant(): void {
    if (this.enfantForm.invalid || !this.enfantIdToUpdate) {
      alert("Veuillez remplir tous les champs obligatoires !");
      return;
    }
    const updatedEnfant: Enfant = this.enfantForm.value;
    this.enfantService.updateEnfant(this.enfantIdToUpdate, updatedEnfant).subscribe({
      next: () => {
        alert('Enfant mis à jour avec succès');
        this.isEditMode = false;
        this.enfantIdToUpdate = null;
        this.enfantForm.reset();
        this.getAllEnfants();
      },
      error: err => console.error('Erreur lors de la mise à jour de l\'enfant:', err)
    });
  }
}
