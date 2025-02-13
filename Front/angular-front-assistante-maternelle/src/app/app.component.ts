import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { EnfantService } from './enfant.service';
import { Observable } from 'rxjs';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsyncPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
})
export class AppComponent {
  title = 'angular-front-assistante-maternelle';
  enfants$: Observable<any[]>;

  constructor(private enfantService: EnfantService) {
    // Appel à l'API
    this.enfants$ = this.enfantService.getEnfants();
  }
}
