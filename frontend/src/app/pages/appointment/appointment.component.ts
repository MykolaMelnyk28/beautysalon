import { Component } from '@angular/core';
import {AppointmentModel} from "../../../model/AppointmentModel";
import {AvailableScheduleModel} from "../../../model/AvailableScheduleModel";
import {ServiceModel} from "../../../model/ServiceModel";
import {TreeNode} from "../../../model/TreeNode";
import {ServiceEntityService} from "../../services/service-entity.service";
import {EmployeeModel} from "../../../model/EmployeeModel";

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent {
  selectedSection: number = 1;
  model: AppointmentModel;
  schedule: AvailableScheduleModel;
  treeRoot: TreeNode | null;
  employees: EmployeeModel[];

  constructor(
    private serviceService: ServiceEntityService
  ) {
    this.employees = [
      new EmployeeModel({
        firstName: "firstname1",
        lastName: "lastname1",
        surName: "surname1",
        email: "email1@gmail.com",
        phoneNumber: "+380682365227",
        position: "position1",
        imageUrl: [],
        workSchedule: []
      })
    ];
    this.treeRoot = this.serviceService.getTree();
    this.model = new AppointmentModel({});
    // new ServiceModel({
    //   name: "service2",
    //   category: "category1.category1_2",
    //   durationInMinute: 30,
    //   price: 50
    // }),
    // new ServiceModel({
    //   name: "service2",
    //   category: "category1.category1_2",
    //   durationInMinute: 30,
    //   price: 50
    // }),
    // new ServiceModel({
    //   name: "service2",
    //   category: "category1.category1_2",
    //   durationInMinute: 30,
    //   price: 50
    // }),
    // new ServiceModel({
    //   name: "service2",
    //   category: "category1.category1_2",
    //   durationInMinute: 30,
    //   price: 50
    // }),
    // new ServiceModel({
    //   name: "service2",
    //   category: "category1.category1_2",
    //   durationInMinute: 30,
    //   price: 50
    // }),
    // new ServiceModel({
    //   name: "service2",
    //   category: "category1.category1_2",
    //   durationInMinute: 30,
    //   price: 50
    // })
    this.schedule = new AvailableScheduleModel({});
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

  handleConfirm(){

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
    if (!this.schedule.startDatetime) {
      return "";
    }
    let end = new Date(this.schedule.startDatetime);
    end.setMinutes(end.getMinutes() + this.totalDuration());
    this.schedule.endDatetime = end;
    return end.toLocaleString(undefined, { year: "numeric", day: "numeric", month: "numeric", hour: "numeric", minute: "numeric"});
  }

  getDate(): string | undefined {
    const startDatetime = this.schedule?.startDatetime;
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
    return this.schedule?.startDatetime?.toLocaleTimeString(undefined, {hour: "numeric", minute: "numeric"});
  }
}
