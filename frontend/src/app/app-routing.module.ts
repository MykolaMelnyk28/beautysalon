import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', component: AppComponent },
  { path: 'services', component: AppComponent },
  { path: 'gallery', component: AppComponent },
  { path: 'aboutus', component: AppComponent },
  { path: 'feedbacks', component: AppComponent },
  { path: 'appointment', component: AppComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
