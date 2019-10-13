import { StorageFormModule } from './storage/storage-form/storage-form.module';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AreaComponent } from './area.component';

const routes: Routes = [
  {
    path: '',
    component: AreaComponent,
    children: [
      {
        path: 'Donations',
        loadChildren: () => import('./donors/donors.module').then(m => m.DonorsModule)
      },
      {
        path: 'Volunteers',
        loadChildren: () => import('./volunteers/volunteers.module').then(m => m.VolunteersModule)
      },
      {
        path: 'Help',
        loadChildren: () =>
          import('./request-help/request-help.module').then(m => m.RequestHelpModule)
      },
      {
        path: 'Storage',
        loadChildren: () => import('./storage/storage.module').then(m => m.StorageModule)
      },
      {
        path: 'Donations/action-form',
        loadChildren: () => import('./action-form/action-form.module').then(m => m.ActionFormModule)
      },
      {
        path: 'Volunteers/action-form',
        loadChildren: () => import('./action-form/action-form.module').then(m => m.ActionFormModule)
      },
      {
        path: 'Help/action-form',
        loadChildren: () => import('./action-form/action-form.module').then(m => m.ActionFormModule)
      },
      {
        path: 'Storage/storage-form',
        loadChildren: () =>
          import('./storage/storage-form/storage-form.module').then(m => m.StorageFormModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AreaRoutingModule {}
