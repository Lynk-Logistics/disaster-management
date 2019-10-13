import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StorageFormRoutingModule } from './storage-form-routing.module';
import { StorageFormComponent } from './storage-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [StorageFormComponent],
  imports: [CommonModule, StorageFormRoutingModule, SharedModule]
})
export class StorageFormModule {}
