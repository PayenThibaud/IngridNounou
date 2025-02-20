import { Routes } from '@angular/router';

import {EnfantsComponent} from './pages/enfants/enfants.component'
import {PlanningComponent} from './pages/planning/planning.component';

export const routes: Routes = [
  {path: "", component: EnfantsComponent},
  {path: "planning", component: PlanningComponent}
];
