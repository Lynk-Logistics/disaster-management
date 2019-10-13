import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VolunteersRoutingModule } from './volunteers-routing.module';
import { SponsorsListComponent } from './sponsors-list/sponsors-list.component';
import { VictimsListComponent } from './victims-list/victims-list.component';
import { VolunteerRegistrationComponent } from './volunteer-registration/volunteer-registration.component';
import { VolunteersComponent } from './volunteers.component';
import { ProfileComponent } from './profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [SponsorsListComponent, VictimsListComponent, VolunteerRegistrationComponent, VolunteersComponent, ProfileComponent],
  imports: [
    CommonModule,
    VolunteersRoutingModule,
    ReactiveFormsModule
  ]
})
export class VolunteersModule { }
