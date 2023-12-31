import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {FeedbackModel} from "../../model/FeedbackModel";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  static baseUrlApiFeedbacks: string = "http://localhost:8080/api/v1/feedbacks";

  constructor(
    private http: HttpClient
  ) {

  }

  create(feedback: FeedbackModel) {
    let url: string = `${FeedbackService.baseUrlApiFeedbacks}`;
    return this.http.post<FeedbackModel>(url, feedback).pipe( catchError(this.handleClientNotFound) );
  }

  getAllFeedbacks(
    page: number = 0,
    limit: number = 10
  ): Observable<any> {
    let url: string = `${FeedbackService.baseUrlApiFeedbacks}`;
    return this.http.get(url,{
      params: new HttpParams().set('limit', limit).set('page', page)
    }).pipe( catchError(this.handleError) );
  }

  handleClientNotFound(error: HttpErrorResponse) {
    if (error.status === 404) {
      return throwError("Client not found. " + error.message);
    } else {
      return throwError(error.message);
    }
  }

  handleError(error: HttpErrorResponse) {
    if(error.status === 404) {
      return throwError("Feedback not found. " + error.message);
    } else {
      return throwError(error.message);
    }
  }

}
