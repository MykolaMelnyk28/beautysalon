import {WorkSchedule} from "./WorkSchedule";
import {ImageModel} from "./ImageModel";
import {EmployeesService} from "../app/services/employees.service";

export class EmployeeModel {
  id: number = 0;
  firstName: string = "";
  lastName: string = "";
  surName: string = "";
  position: string = "";
  username: string = "";
  email: string = "";
  phoneNumber: string = "";
  imageUrl: ImageModel[] = [EmployeesService.defaultUserPhoto];
  workSchedule: WorkSchedule[] = [];

  constructor(partial: Partial<EmployeeModel>) {
    Object.assign(this, partial);
  }

  // getFirstImageOrDefault(): string {
  //   return (this.imageUrl.length >= 1) ? this.imageUrl[0] : 'assets/default_user_photo.png';
  // }
  // getOtherImages(): string[] {
  //   return (this.imageUrl.length <= 1) ? [] : this.imageUrl.slice(1);
  // }
}
