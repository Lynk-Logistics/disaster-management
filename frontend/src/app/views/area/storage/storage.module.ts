import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StorageRoutingModule } from './storage-routing.module';
import { StorageComponent } from './storage.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [StorageComponent],
  imports: [CommonModule, StorageRoutingModule, SharedModule]
})
export class StorageModule {}
