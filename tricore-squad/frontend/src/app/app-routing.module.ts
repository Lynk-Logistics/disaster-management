import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path: 'home',
    loadChildren: './common/common.module#CommonsModule'
  },
  {
    path: 'victim',
    loadChildren: './victim/victim.module#VictimModule'
  },
  {
    path: 'sponsor',
    loadChildren: './sponsors/sponsors.module#SponsorsModule'
  },
  {
    path: 'volunteer',
    loadChildren: './volunteers/volunteers.module#VolunteersModule'
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
