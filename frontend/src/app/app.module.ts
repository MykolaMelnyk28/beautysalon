import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {ServicesPageComponent} from "./pages/services-page/services-page.component";
import {NgOptimizedImage} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {AboutusComponent} from "./pages/aboutus/aboutus.component";
import { FeedbacksPageComponent } from './pages/feedbacks-page/feedbacks-page.component';
import {GalleryPageComponent} from "./pages/gallery-page/gallery-page.component";
import { FrameLightComponent } from './components/frame-light/frame-light.component';
import { CreateFeedbackDialogComponent } from './components/feedbacks/create-feedback-dialog/create-feedback-dialog.component';
import {FormsModule} from "@angular/forms";
import { ViewEmployeeDialogComponent } from './components/aboutus/view-employee-dialog/view-employee-dialog.component';
import { TreeNodeComponent } from './components/services/tree-node/tree-node.component';
import { TreeComponent } from './components/services/tree/tree.component';
import { ViewServiceDialogComponent } from './components/services/view-service-dialog/view-service-dialog.component';
import {ServiceEntityService} from "./services/service-entity.service";
import { ServiceListComponent } from './components/appointment/service-list/service-list.component';
import {AppointmentComponent} from "./pages/appointment/appointment.component";
import {ImageService} from "./services/image.service";
import {FeedbackService} from "./services/feedback.service";
import {ClientService} from "./services/client.service";
import {EmployeesService} from "./services/employees.service";
import {AppointmentService} from "./services/appointment.service";
import {ButtonComponent} from "./components/button/button.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    ButtonComponent,
    HomePageComponent,
    ButtonComponent,
    ServicesPageComponent,
    AboutusComponent,
    AppointmentComponent,
    GalleryPageComponent,
    FeedbacksPageComponent,
    FrameLightComponent,
    CreateFeedbackDialogComponent,
    ViewEmployeeDialogComponent,
    TreeNodeComponent,
    TreeComponent,
    ViewServiceDialogComponent,
    ServiceListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [
    ServiceEntityService,
    ImageService,
    FeedbackService,
    ClientService,
    EmployeesService,
    AppointmentService
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
