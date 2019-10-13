import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { EmergencyInfoComponent } from './emergency-info/emergency-info.component';
import { MobileNumberComponent } from './mobile-number/mobile-number.component';
import { FindmissingMarksafeComponent } from './findmissing-marksafe/findmissing-marksafe.component';
import { DonateComponent } from './donate/donate.component';
import { MissingComponent } from './missing/missing.component';
import { RecoverComponent } from './recover/recover.component';


const routes: Routes = [
  {
    path: 'emergency',
    component: EmergencyInfoComponent
  },
  {
    path: 'enter-mobile',
    component: MobileNumberComponent
  },
  {
    path: 'find-and-mark',
    component: FindmissingMarksafeComponent
  },
  {
    path: 'donate',
    component: DonateComponent
  },
  {
    path: 'missing',
    component: MissingComponent
  },
  {
    path: 'recovered',
    component: RecoverComponent
  },
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommonRoutingModule { }
