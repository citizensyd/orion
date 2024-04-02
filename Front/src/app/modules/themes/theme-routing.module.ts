import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ThemesComponent} from "./component/themes/themes.component";


const routes: Routes = [
  { title: 'Thèmes', path: '', component: ThemesComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThemeRoutingModule { }
