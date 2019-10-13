import { ActionFormRoutingModule } from './action-form-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActionFormComponent } from './action-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [ActionFormComponent],
  imports: [CommonModule, ActionFormRoutingModule, SharedModule]
})
export class ActionFormModule {}
