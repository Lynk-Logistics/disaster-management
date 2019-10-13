import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SponsorsRoutingModule } from './sponsors-routing.module';
import { VolunteersListComponent } from './volunteers-list/volunteers-list.component';
import { SponsorRegistrationComponent } from './sponsor-registration/sponsor-registration.component';
import { VictimsListComponent } from './victims-list/victims-list.component';
import { SponsorsComponent } from './sponsors.component';
import { ProfileComponent } from './profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [VolunteersListComponent, SponsorRegistrationComponent, VictimsListComponent, SponsorsComponent, ProfileComponent],
  imports: [
    CommonModule,
    SponsorsRoutingModule,
    ReactiveFormsModule
  ]
})
export class SponsorsModule { }
