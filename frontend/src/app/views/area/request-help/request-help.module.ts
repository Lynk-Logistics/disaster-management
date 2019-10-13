import { SharedModule } from 'src/app/shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RequestHelpRoutingModule } from './request-help-routing.module';
import { RequestHelpComponent } from './request-help.component';

@NgModule({
  declarations: [RequestHelpComponent],
  imports: [CommonModule, RequestHelpRoutingModule, SharedModule]
})
export class RequestHelpModule {}
