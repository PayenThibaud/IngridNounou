import { Component } from '@angular/core';
import { EnfantService } from '../enfant.service';
import { Observable } from 'rxjs';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-enfant-list',
  templateUrl: './enfant-list.component.html',
  imports: [
    AsyncPipe
  ],
  styleUrls: ['./enfant-list.component.css']
})
export class EnfantListComponent {
  enfants$: Observable<any[]>;

  constructor(private enfantService: EnfantService) {
    this.enfants$ = this.enfantService.getEnfants();
  }
}

