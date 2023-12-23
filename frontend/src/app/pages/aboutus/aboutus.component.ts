import { Component } from '@angular/core';
import {EmployeeModel} from "../../../model/EmployeeModel";

@Component({
  selector: 'app-aboutus',
  templateUrl: './aboutus.component.html',
  styleUrls: ['./aboutus.component.css']
})
export class AboutusComponent {
  employees: EmployeeModel[];


  constructor() {
    this.employees = [];
  }

  getFirstImageOrDefault(e: EmployeeModel): string {
      return e.imageUrl.length > 0
          ? e.imageUrl[0]
          : 'assets/default_user_photo.png';
  }
}
