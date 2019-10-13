import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VolunteersComponent } from './volunteers.component';

const routes: Routes = [{ path: '', component: VolunteersComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VolunteersRoutingModule {}
