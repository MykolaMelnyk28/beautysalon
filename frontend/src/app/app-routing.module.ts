import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
<<<<<<< Updated upstream
import {AboutusComponent} from "./pages/aboutus/aboutus.component";
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {ServicesPageComponent} from "./pages/services-page/services-page.component";
import {GalleryPageComponent} from "./pages/gallery-page/gallery-page.component";
import {FeedbacksPageComponent} from "./pages/feedbacks-page/feedbacks-page.component";
import {AppointmentComponent} from "./pages/appointment/appointment.component";

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', title: "Головна", component: HomePageComponent },
  { path: 'services', title: "Послуги", component: ServicesPageComponent },
  { path: 'gallery', title: "Галерея", component: GalleryPageComponent },
  { path: 'aboutus', title: "Про салон", component: AboutusComponent },
  { path: 'feedbacks', title: "Відгуки", component: FeedbacksPageComponent },
  { path: 'appointment', title: "Запис на прийом", component: AppointmentComponent }
=======
import {AppComponent} from "./app.component";
import {HomePageComponent} from "./pages/home-page/home-page.component";

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', component: HomePageComponent },
  { path: 'services', component: AppComponent },
  { path: 'gallery', component: AppComponent },
  { path: 'aboutus', component: AppComponent },
  { path: 'feedbacks', component: AppComponent },
  { path: 'appointment', component: AppComponent }
>>>>>>> Stashed changes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
