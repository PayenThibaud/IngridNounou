import { Routes } from '@angular/router';

import {EnfantsComponent} from './pages/enfants/enfants.component'
import {PlanningComponent} from './pages/planning/planning.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.compnent';

export const routes: Routes = [
  {path: "", component: EnfantsComponent},
  {path: "planning", component: PlanningComponent},
  {path: "profile", component: ProfileComponent},
  {path: "login", component: LoginComponent}

];
