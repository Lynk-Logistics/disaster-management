import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VolunteersRoutingModule } from './volunteers-routing.module';
import { VolunteersComponent } from './volunteers.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [VolunteersComponent],
  imports: [CommonModule, VolunteersRoutingModule, SharedModule]
})
export class VolunteersModule {}
