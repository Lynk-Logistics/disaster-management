import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VictimRoutingModule } from './victim-routing.module';
import { VictimInfoComponent } from './victim-info/victim-info.component';
import { VolunteersListComponent } from './volunteers-list/volunteers-list.component';
import { VictimComponent } from './victim.component';
import { ProfileComponent } from './profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [VictimInfoComponent, VolunteersListComponent, VictimComponent, ProfileComponent],
  imports: [
    CommonModule,
    VictimRoutingModule,
    ReactiveFormsModule
  ]
})
export class VictimModule { }
