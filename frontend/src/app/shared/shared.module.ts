import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './modules/material.module';
import { RouterModule } from '@angular/router';
import { MatSnackBarModule } from '@angular/material/snack-bar';

const allModules = [
  CommonModule,
  MaterialModule,
  FormsModule,
  ReactiveFormsModule,
  RouterModule,
  MatSnackBarModule
];
const allEntryComponents = [];
const allComponents = [];
const allDirectives = [];
const allProviders = [];

@NgModule({
  declarations: [[...allComponents], [...allDirectives], [...allEntryComponents]],
  imports: allModules,
  providers: allProviders,
  entryComponents: allEntryComponents,
  exports: [[...allModules], [...allComponents], [...allDirectives]]
})
export class SharedModule {}
