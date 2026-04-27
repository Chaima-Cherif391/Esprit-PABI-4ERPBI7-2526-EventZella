import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login/login.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardCeoComponent } from './Ceo/dashboard-ceo/dashboard-ceo.component';
import { DashboardMarketingComponent } from './Marketing/dashboard-marketing/dashboard-marketing.component';
import { ContentCeoComponent } from './Ceo/content-ceo/content-ceo.component';
import { PriceRegressionComponent } from './Ceo/price-regression/price-regression.component';
import { ForecastComponent } from './forecast/forecast.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardCeoComponent,
    DashboardMarketingComponent,
    ContentCeoComponent,
    PriceRegressionComponent,
    ForecastComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    HttpClientModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
