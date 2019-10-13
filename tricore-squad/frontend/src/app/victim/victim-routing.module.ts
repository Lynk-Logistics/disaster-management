import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VolunteersListComponent } from './volunteers-list/volunteers-list.component';
import { VictimInfoComponent } from './victim-info/victim-info.component';
import { VictimComponent } from './victim.component';
import { ProfileComponent } from './profile/profile.component';


const routes: Routes = [
  {
    path: 'new',
    component: VictimInfoComponent
  },
  {
    path: '',
    component: VictimComponent,
    children: [
      {
        path: 'volunteers-list',
        component: VolunteersListComponent
      },
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: '',
        redirectTo: 'profile',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VictimRoutingModule { }
