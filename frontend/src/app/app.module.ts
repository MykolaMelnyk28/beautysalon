import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {ServicesPageComponent} from "./pages/services-page/services-page.component";
import {NgOptimizedImage} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {ButtonComponent} from "./components/button/button.component";
import {AboutusComponent} from "./pages/aboutus/aboutus.component";
import { FeedbacksPageComponent } from './pages/feedbacks-page/feedbacks-page.component';
import {GalleryPageComponent} from "./pages/gallery-page/gallery-page.component";
import {AppointmentComponent} from "./pages/appointment/appointment.component";
import { FrameLightComponent } from './components/frame-light/frame-light.component';
import { CreateFeedbackDialogComponent } from './components/feedbacks/create-feedback-dialog/create-feedback-dialog.component';
import {FormsModule} from "@angular/forms";
import { ViewEmployeeDialogComponent } from './components/aboutus/view-employee-dialog/view-employee-dialog.component';
import { TreeNodeComponent } from './components/services/tree-node/tree-node.component';
import { TreeComponent } from './components/services/tree/tree.component';
import { ViewServiceDialogComponent } from './components/services/view-service-dialog/view-service-dialog.component';
import {ServiceEntityService} from "./services/service-entity.service";

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    ButtonComponent,
    HomePageComponent,
    ServicesPageComponent,
    AboutusComponent,
    GalleryPageComponent,
    FeedbacksPageComponent,
    AppointmentComponent,
    FrameLightComponent,
    CreateFeedbackDialogComponent,
    ViewEmployeeDialogComponent,
    TreeNodeComponent,
    TreeComponent,
    ViewServiceDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    HttpClientModule,
    FormsModule
  ],
  providers: [ServiceEntityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
