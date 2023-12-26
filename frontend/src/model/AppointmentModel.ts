import {EmployeeModel} from "./EmployeeModel";
import {ClientModel} from "./ClientModel";
import {ServiceModel} from "./ServiceModel";
export class AppointmentModel {
  client: ClientModel = new ClientModel({});
  master: EmployeeModel = new EmployeeModel({});
  totalDurationInMinute: number = 0;
  totalPrice: number = 0;
  datetime: Date = new Date();
  services: Set<ServiceModel> = new Set<ServiceModel>();

  constructor(partial: Partial<AppointmentModel>) {
      Object.assign(this, partial);
  }
}
