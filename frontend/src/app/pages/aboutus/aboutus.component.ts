import { Component } from '@angular/core';
import { EmployeeModel } from "../../../model/EmployeeModel";
import {EmployeesService} from "../../services/employees.service";

@Component({
  selector: 'app-aboutus',
  templateUrl: './aboutus.component.html',
  styleUrls: ['./aboutus.component.css']
})
export class AboutusComponent {
  employees: EmployeeModel[] = [];
  isDialogVisible: boolean = false;
  selectedEmployee: EmployeeModel;

  constructor(
    public employeeService: EmployeesService
  ) {
    this.employeeService.getAllMasters().subscribe(data => {
      this.employees.push(...data);
    });
    console.log(this.employees);
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
