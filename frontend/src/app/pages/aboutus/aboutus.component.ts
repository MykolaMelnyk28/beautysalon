import { Component } from '@angular/core';
import { EmployeeModel } from "../../../model/EmployeeModel";

@Component({
  selector: 'app-aboutus',
  templateUrl: './aboutus.component.html',
  styleUrls: ['./aboutus.component.css']
})
export class AboutusComponent {
  employees: EmployeeModel[];
  isDialogVisible: boolean = false;
  selectedEmployee: EmployeeModel;

  constructor() {
    this.employees = [];
    this.selectedEmployee = new EmployeeModel({});
  }

  openEmployeeDialog(employee: EmployeeModel) {
    this.selectedEmployee = employee;
    this.isDialogVisible = true;
  }

  onDialogClose() {
    this.isDialogVisible = false;
  }
}
