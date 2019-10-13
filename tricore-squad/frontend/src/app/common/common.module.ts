import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CommonRoutingModule } from './common-routing.module';
import { HomeComponent } from './home/home.component';
import { EmergencyInfoComponent } from './emergency-info/emergency-info.component';
import { MobileNumberComponent } from './mobile-number/mobile-number.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FindmissingMarksafeComponent } from './findmissing-marksafe/findmissing-marksafe.component';
import { DonateComponent } from './donate/donate.component';
import { MissingComponent } from './missing/missing.component';
import { RecoverComponent } from './recover/recover.component';


@NgModule({
  declarations: [HomeComponent, EmergencyInfoComponent, MobileNumberComponent, FindmissingMarksafeComponent, DonateComponent, MissingComponent, RecoverComponent],
  imports: [
    CommonModule,
    CommonRoutingModule,
    ReactiveFormsModule
  ]
})
export class CommonsModule { }
