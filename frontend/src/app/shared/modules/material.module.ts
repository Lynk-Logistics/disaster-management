import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { NgModule } from '@angular/core';

// Material Components
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { MatExpansionModule } from '@angular/material/expansion';

const MatModules = [
  MatCardModule,
  MatButtonModule,
  MatSelectModule,
  MatAutocompleteModule,
  MatSidenavModule,
  MatIconModule,
  MatToolbarModule,
  MatDividerModule,
  MatListModule,
  MatMenuModule,
  MatInputModule,
  MatIconModule,
  MatToolbarModule,
  MatInputModule,
  FlexLayoutModule,
  MatTableModule,
  MatRadioModule,
  MatExpansionModule
];

@NgModule({
  imports: [MatModules],
  exports: [MatModules]
})
export class MaterialModule {}
