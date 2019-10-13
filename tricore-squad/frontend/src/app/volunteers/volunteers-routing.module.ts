import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VictimsListComponent } from './victims-list/victims-list.component';
import { SponsorsListComponent } from './sponsors-list/sponsors-list.component';
import { VolunteerRegistrationComponent } from './volunteer-registration/volunteer-registration.component';
import { VolunteersComponent } from './volunteers.component';
import { ProfileComponent } from './profile/profile.component';


const routes: Routes = [
  {
    path: 'new',
    component: VolunteerRegistrationComponent
  },
  {
    path: '',
    component: VolunteersComponent,
    children: [
      {
        path: 'victims-list',
        component: VictimsListComponent
      },
      {
        path: 'sponsors-list',
        component: SponsorsListComponent
      },
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: '',
        redirectTo: 'victims-list',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VolunteersRoutingModule { }
