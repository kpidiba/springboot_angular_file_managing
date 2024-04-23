import { AppComponent } from './app/app.component';
import { bootstrapApplication } from '@angular/platform-browser';
import { Routes, provideRouter } from '@angular/router';
import { PageNotFoundComponent } from './shared/components/page-not-found/page-not-found.component';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { provideToastr } from 'ngx-toastr';


const routes: Routes = [
  {
    title: 'UPLOAD LOGIN',
    path: '',
    pathMatch: "full",
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./features/uploads/route'),
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
]

bootstrapApplication(AppComponent, { providers: [provideRouter(routes), provideAnimations(), provideToastr(), provideHttpClient()] }).catch((err) => console.error(err));
