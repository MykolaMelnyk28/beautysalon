import {ClientModel} from "./ClientModel";
import {EmployeeModel} from "./EmployeeModel";
import {ServiceModel} from "./ServiceModel";
import {AvailableScheduleModel} from "./AvailableScheduleModel";

export class Appointment {
  services: Set<ServiceModel> = new Set<ServiceModel>();
  totalDurationInMinute: number = 0;
  totalPrice: number = 0;
  master: EmployeeModel | undefined;
  firstNameClient: string | undefined;
  lastNameClient: string | undefined;
  emailClient: string | undefined;
  phoneNumberClient: string | undefined;
  appointmentDate: Date = new Date();
  schedule: AvailableScheduleModel = new AvailableScheduleModel({});


  constructor(partial: Partial<Appointment>) {
    Object.assign(this, partial);
  }
}
