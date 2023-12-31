import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ClientModel} from "../../model/ClientModel";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  static baseUrlApiClients: string = "http://localhost:8080/api/v1/clients";

  constructor(
    private http: HttpClient
  ) { }

  getByEmail(email: string): Observable<ClientModel> {
    let url: string = `${ClientService.baseUrlApiClients}/${email}`;
    return this.http.get<ClientModel>(url).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 404) {
      return throwError(new Observable<ClientModel>());
    } else {
      console.error('An error occurred:', error);
      return throwError('Error');
    }
  }
}
