import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AboutusComponent} from "./pages/aboutus/aboutus.component";
import {AppointmentComponent} from "./pages/appointment/appointment.component";
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {ServicesPageComponent} from "./pages/services-page/services-page.component";
import {GalleryPageComponent} from "./pages/gallery-page/gallery-page.component";
import {FeedbacksPageComponent} from "./pages/feedbacks-page/feedbacks-page.component";

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', component: HomePageComponent },
  { path: 'services', component: ServicesPageComponent },
  { path: 'gallery', component: GalleryPageComponent },
  { path: 'aboutus', component: AboutusComponent },
  { path: 'feedbacks', component: FeedbacksPageComponent },
  { path: 'appointment', component: AppointmentComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
