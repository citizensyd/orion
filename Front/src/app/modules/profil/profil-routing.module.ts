import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProfilComponent} from "./component/profil/profil.component";


const routes: Routes = [
  { title: 'Profil', path: '', component: ProfilComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfilRoutingModule { }
