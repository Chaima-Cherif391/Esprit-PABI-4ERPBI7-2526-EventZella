import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login/login.component';
import { DashboardCeoComponent } from './Ceo/dashboard-ceo/dashboard-ceo.component';
import { DashboardMarketingComponent } from './Marketing/dashboard-marketing/dashboard-marketing.component';
import { ContentCeoComponent } from './Ceo/content-ceo/content-ceo.component';
import { AuthGuard } from './guards/auth.guard';
import { PriceRegressionComponent } from './Ceo/price-regression/price-regression.component';

const routes: Routes = [
// {path : '', redirectTo : 'login', pathMatch : 'full'},
// {path : 'login', component : LoginComponent},
// {path :'dashboard-ceo', component:DashboardCeoComponent},
// {path :'dashboard-marketing', component:DashboardMarketingComponent},
// { path: 'content-ceo',      component: ContentCeoComponent },
    


 { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard-ceo', component: DashboardCeoComponent,
    canActivate: [AuthGuard], data: { role: 'CEO' } },
  { path: 'dashboard-marketing', component: DashboardMarketingComponent,
    canActivate: [AuthGuard], data: { role: 'MARKETING' } },
  { path: 'content-ceo',      component: ContentCeoComponent,
    canActivate: [AuthGuard], data: { role: 'CEO' } },
  { path: 'price-regression', component: PriceRegressionComponent },


// if (user.role === 'CEO') {
//   this.router.navigate(['/dashboard-ceo']);
// } else if (user.role === 'MARKETING') {
//   this.router.navigate(['/dashboard-marketing']);
// }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
