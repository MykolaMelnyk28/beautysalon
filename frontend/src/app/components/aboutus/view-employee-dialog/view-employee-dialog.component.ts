import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EmployeeModel} from "../../../../model/EmployeeModel";
import {EmployeesService} from "../../../services/employees.service";

@Component({
  selector: 'app-view-employee-dialog',
  templateUrl: './view-employee-dialog.component.html',
  styleUrls: ['./view-employee-dialog.component.css']
})
export class ViewEmployeeDialogComponent {
  @Input() isVisible: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Input() item: EmployeeModel = new EmployeeModel({});

  constructor(
    public employeeService: EmployeesService
  ) {}

  onClose() {
    this.isVisible = false;
    this.close.emit();
  }
}
