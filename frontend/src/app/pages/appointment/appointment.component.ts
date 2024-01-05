import { Component } from '@angular/core';
import {ServiceModel} from "../../../model/ServiceModel";
import {TreeNode} from "../../../model/TreeNode";
import {ServiceEntityService} from "../../services/service-entity.service";
import {EmployeeModel} from "../../../model/EmployeeModel";
import {Appointment} from "../../../model/Appointment";
import {AppointmentModel} from "../../../model/AppointmentModel";
import {ClientModel} from "../../../model/ClientModel";
import {EmployeesService} from "../../services/employees.service";
import {AppointmentService} from "../../services/appointment.service";

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent {
  selectedSection: number = 1;
  model: Appointment;
  treeRoot: TreeNode | null;
  employees: EmployeeModel[] = [];
  datetimeAvailable: boolean | undefined;

  constructor(
    private serviceService: ServiceEntityService,
    public employeeService: EmployeesService,
    private appointmentService: AppointmentService
  ) {
    this.employeeService.getAllMasters().subscribe(data => {
      this.employees.push(...data);
    });
    this.treeRoot = this.serviceService.getTree();
    this.model = new Appointment({});
  }

  hasContent(): boolean {
    return this.treeRoot !== null;
  }

  handleNodeClick(node: TreeNode) {
    if (node.value) {
      this.model.services.add(node.value);
    }
  }

  handleRemoveService(e: ServiceModel) {
    this.model.services.delete(e);
  }

  handleEmployeeClick(e: EmployeeModel) {
    this.model.master = e;
  }

  backPart() {
    if (this.selectedSection === 1) {
      return;
    }
    this.selectedSection--;
  }

  nextPart() {
    if (this.selectedSection === 5) {
      return;
    }
    this.selectedSection++;
  }

  hasNext(): boolean {
    switch (this.selectedSection) {
      case 1:
        return this.model.services.size > 0;
      case 2:
        return this.model.master !== undefined;
      case 3:
        return this.model.schedule.startDatetime !== undefined &&
          this.model.schedule.endDatetime !== undefined &&
          (this.datetimeAvailable !== undefined && this.datetimeAvailable);
      case 4:
        return this.model.firstNameClient !== undefined &&
          this.model.lastNameClient !== undefined &&
          this.model.emailClient !== undefined &&
          this.model.phoneNumberClient !== undefined;
      default:
        return true;
    }
  }

  handleConfirm(){
    let appointment: AppointmentModel | null = this.appointmentService.completeModel(this.model);

    if (appointment) {
      this.appointmentService.createAppointment(appointment).subscribe(data => {
        console.log(data);
      });
    }
  }



  totalPrice(): number {
    let totalPrice = 0;
    this.model.services.forEach(x => {
      totalPrice += x.price;
    });
    return totalPrice;
  }

  totalDuration(): number {
    let totalDuration = 0;
    this.model.services.forEach(x => {
      totalDuration += x.durationInMinute;
    });
    return totalDuration;
  }

  getEndDate(): string {
    if (!this.model.schedule.startDatetime) {
      return "";
    }
    let end = new Date(this.model.schedule.startDatetime);
    end.setMinutes(end.getMinutes() + this.totalDuration());
    this.model.schedule.endDatetime = end;
    return end.toLocaleString(undefined, { year: "numeric", day: "numeric", month: "numeric", hour: "numeric", minute: "numeric"});
  }

  getDataString(date: Date | undefined): string {
    if (date) {
      let dateS = date.toLocaleString("uk_UA");
      return dateS.replace("T", " ").replaceAll("-", ".");
    }
    return "";
  }

  handleAvailableDatatimeBtnClick() {
    const startDatetime: Date | undefined = this.model.schedule.startDatetime;
    const endDatetime: Date | undefined = this.model.schedule.endDatetime;
    const master: EmployeeModel | undefined = this.model.master;

    if (startDatetime && endDatetime && master) {
      let startD = new Date(startDatetime);
      let endD = new Date(endDatetime);
      const userTimezoneOffset = startD.getTimezoneOffset();

      const correctedStartDatetime = new Date(startD.getTime() - (userTimezoneOffset * 60000));
      const correctedEndDatetime = new Date(endD.getTime() - (userTimezoneOffset * 60000));

      this.employeeService.getAvailableDatetimeMaster(
        correctedStartDatetime, correctedEndDatetime, master
      ).subscribe(data => {
        this.datetimeAvailable = data.accessibility;
      });
    }
  }

  getDatetimeAvailability(): string {
    if (this.datetimeAvailable === undefined) {
      return " ";
    } else if (this.datetimeAvailable) {
      return "Доступно";
    } else {
      return "Не доступно";
    }
  }

  getDate(): string | undefined {
    const startDatetime = this.model.schedule.startDatetime;
    return (!startDatetime)
      ? undefined
      : startDatetime.toLocaleDateString(undefined, {
      year: "numeric",
      day: "numeric",
      month: "numeric",
      hour: "numeric",
      minute: "numeric",
    });
  }

  getTime(): string | undefined {
    return this.model.schedule.startDatetime?.toLocaleTimeString(undefined, {hour: "numeric", minute: "numeric"});
  }
}
