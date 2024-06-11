import { Injectable } from '@angular/core';
import {AppointmentModel} from "../../model/AppointmentModel";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Appointment} from "../../model/Appointment";
import {EmployeeModel} from "../../model/EmployeeModel";
import {ClientModel} from "../../model/ClientModel";

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  static baseUrlApiClients: string = "http://34.227.194.16:8080/api/v1/appointments";

  constructor(
    private http: HttpClient
  ) { }

  createAppointment(appointment: AppointmentModel): Observable<AppointmentModel> {
    console.log(JSON.stringify(appointment));
    let url: string = `${AppointmentService.baseUrlApiClients}`;
    return this.http.post<AppointmentModel>(url, appointment);
  }

  completeModel(appointment: Appointment): AppointmentModel | null {
    const startDatetime: Date | undefined = appointment.schedule.startDatetime;

    if (startDatetime) {
      let startD = new Date(startDatetime);
      const userTimezoneOffset = startD.getTimezoneOffset();
      let appointmentDate: Date = new Date(startD.getTime() - (userTimezoneOffset * 60000));
      let a: AppointmentModel = {
        services: [...appointment.services],
        master: appointment.master ?? new EmployeeModel({}),
        totalPrice: appointment.totalPrice,
        totalDurationInMinute: appointment.totalDurationInMinute,
        client: new ClientModel({
          firstName: appointment.firstNameClient,
          lastName: appointment.lastNameClient,
          email: appointment.emailClient,
          phoneNumber: appointment.phoneNumberClient
        }),
        appointmentDate: appointmentDate
      };
      a.master.imageUrl = [];
      return a;
    }
    return null;
  }
}
