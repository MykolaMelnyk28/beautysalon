import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {EmployeeModel} from "../../model/EmployeeModel";
import {ResponseAvailabilityDateTime} from "../../model/ResponseAvailabilityDateTime";

@Injectable({
  providedIn: 'root'
})
export class EmployeesService {

  static baseUrlApiClients: string = "http://localhost:8080/api/v1/employees";

  constructor(
    private http: HttpClient
  ) { }

  getAllMasters(): Observable<EmployeeModel[]> {
    let url: string = `${EmployeesService.baseUrlApiClients}/masters`;
    return this.http.get<EmployeeModel[]>(url).pipe(
      map(masters => {
        return masters.map(master => {
          if ((master.imageUrl && master.imageUrl.length === 0) || !master.imageUrl) {
            master.imageUrl = ["assets/default_user_photo.png"];
          }
          return master;
        });
      })
    );
  }

  getAvailableDatetimeMaster(start: Date, end: Date, e: EmployeeModel): Observable<ResponseAvailabilityDateTime> {
    let url: string = `http://localhost:8080/api/v1/employees/masters/1/availability`;

    let startD: string = new Date(start).toJSON();
    let endD: string = new Date(end).toJSON();
    console.log(startD);
    console.log(endD);
    let body: any = {
      startDatetime: startD,
      endDatetime: endD
    };
    return this.http.post<ResponseAvailabilityDateTime>(url, body).pipe(
      map(ms => {
      return ms;
    }));
  }

  getFirstImageOrDefault(employee: EmployeeModel | undefined): string {
    if (employee !== undefined) {
      return (!employee.imageUrl || employee.imageUrl.length === 0)
        ? 'assets/default_user_photo.png'
        : employee.imageUrl[0];
    }
    return 'assets/default_user_photo.png';
  }

  getOtherImages(employee: EmployeeModel): string[] {
    return (employee.imageUrl.length === 0)
      ? []
      : employee.imageUrl.slice(1);
  }
}
