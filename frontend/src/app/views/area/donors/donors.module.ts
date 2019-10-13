import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DonorsRoutingModule } from './donors-routing.module';
import { DonorsComponent } from './donors.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [DonorsComponent],
  imports: [CommonModule, DonorsRoutingModule, SharedModule]
})
export class DonorsModule {}
