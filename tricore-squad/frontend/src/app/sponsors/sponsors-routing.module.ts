import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VolunteersListComponent } from './volunteers-list/volunteers-list.component';
import { VictimsListComponent } from './victims-list/victims-list.component';
import { SponsorRegistrationComponent } from './sponsor-registration/sponsor-registration.component';
import { SponsorsComponent } from './sponsors.component';
import { ProfileComponent } from './profile/profile.component';


const routes: Routes = [
  {
    path: 'new',
    component: SponsorRegistrationComponent
  },
  {
    path: '',
    component: SponsorsComponent,
    children: [
      {
        path: 'volunteers-list',
        component: VolunteersListComponent
      },
      {
        path: 'victims-list',
        component: VictimsListComponent
      },
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: '',
        redirectTo: 'volunteers-list',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SponsorsRoutingModule { }
