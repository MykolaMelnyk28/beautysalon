import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {EmployeeModel} from "../../model/EmployeeModel";
import {ResponseAvailabilityDateTime} from "../../model/ResponseAvailabilityDateTime";
import {Page} from "../../model/Page";
import {ImageModel} from "../../model/ImageModel";


@Injectable({
  providedIn: 'root'
})
export class EmployeesService {

  static baseUrlApiEmployees: string = "http://localhost:8080/api/v1/employees";
  static defaultUserPhoto: string = 'assets/default_user_photo.png';

  constructor(
    private http: HttpClient
  ) { }

  getAllMasters(): Observable<EmployeeModel[]> {
    const url: string = `${EmployeesService.baseUrlApiEmployees}/masters`;
    return this.http.get<Page<EmployeeModel>>(url).pipe(
      map(mastersPage => {
        return mastersPage.content.map(master => {
          if ((master.imageUrl && !master.imageUrl.length) || !master.imageUrl) {
            master.imageUrl = [EmployeesService.defaultUserPhoto];
          }
          return master;
        });
      })
    );
  }

  getAvailableDatetimeMaster(start: Date, end: Date, e: EmployeeModel): Observable<ResponseAvailabilityDateTime> {
    const url: string = `${EmployeesService.baseUrlApiEmployees}/masters/${e.id}/availability`;
    let body: any = {
      startDatetime: new Date(start).toJSON(),
      endDatetime: new Date(end).toJSON()
    };
    return this.http.post<ResponseAvailabilityDateTime>(url, body).pipe(
      map(ms => ms));
  }

  getPreviewImage(username: string): Observable<ImageModel> {
    const url: string = `${EmployeesService.baseUrlApiEmployees}/${username}/images/preview`;
    return this.http.get<ImageModel>(url);
  }

  getFirstImageOrDefault(employee: EmployeeModel | undefined): string {
    return (employee?.imageUrl.length) ? employee.imageUrl[0] : EmployeesService.defaultUserPhoto;
  }
  getOtherImages(employee: EmployeeModel): string[] {
    return (employee?.imageUrl.length <= 1) ? [] : employee.imageUrl.slice(1);
  }
}
