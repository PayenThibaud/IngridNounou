import { Component, OnInit } from '@angular/core';
import { PlanningService } from '../../services/planning.service';
import { Planning } from '../../types/planning.type';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-planning',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './planning.component.html',
  styleUrls: ['./planning.component.css']
})

export class PlanningComponent implements OnInit {
  plannings: Planning[] = [];
  planningForm: FormGroup;
  isEditMode = false;
  planningIdToUpdate: number | null = null;

  constructor(private planningService: PlanningService, private fb: FormBuilder, private router: Router) {
    this.planningForm = this.fb.group({
      date: [''],
      heureArriver: [''],
      heureFin: [''],
      idEnfant: [''],
      nomEnfant: ['']
    });
  }

  ngOnInit(): void {
    this.getAllPlannings();
  }

  getAllPlannings(): void {
    this.planningService.getAllPlannings().subscribe(data => {
      this.plannings = data;
    });
  }

  addPlanning(): void {
    if (this.planningForm.invalid) {
      alert("Veuillez remplir tous les champs obligatoires !");
      return;
    }
    const newPlanning: Planning = this.planningForm.value;
    this.planningService.addPlanning(newPlanning).subscribe(() => {
      alert('Planning ajouté avec succès');
      this.planningForm.reset();
      this.getAllPlannings();
    });
  }

  deletePlanning(id: number): void {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce planning ?")) {
      this.planningService.deletePlanning(id.toString()).subscribe({
        next: () => {
          alert('Planning supprimé avec succès');
          this.getAllPlannings();
        },
        error: err => {
          console.error('Erreur lors de la suppression du planning:', err);
        }
      });
    }
  }

  editPlanning(planning: Planning): void {
    this.isEditMode = true;
    this.planningIdToUpdate = planning.idHorraire;
    this.planningForm.patchValue({
      date: planning.date,
      heureArriver: planning.heureArriver,
      heureFin: planning.heureFin,
      idEnfant: planning.idEnfant,
      nomEnfant: planning.nomEnfant
    });
  }

  updatePlanning(): void {
    if (this.planningForm.invalid || this.planningIdToUpdate === null) {
      alert("Veuillez remplir tous les champs obligatoires !");
      return;
    }
    const updatedPlanning: Planning = this.planningForm.value;
    this.planningService.updatePlanning(this.planningIdToUpdate.toString(), updatedPlanning).subscribe({
      next: () => {
        alert('Planning mis à jour avec succès');
        this.isEditMode = false;
        this.planningIdToUpdate = null;
        this.planningForm.reset();
        this.getAllPlannings();
      },
      error: err => console.error('Erreur lors de la mise à jour du planning:', err)
    });
  }
}
