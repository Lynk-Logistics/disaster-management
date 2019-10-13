import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ActionFormComponent } from './action-form.component';

const routes: Routes = [{ path: '', component: ActionFormComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActionFormRoutingModule {}
