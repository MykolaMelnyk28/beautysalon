import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {ImageModel} from "../../model/ImageModel";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  static baseUrlApiImage: string = "http://34.227.194.16:8080/api/v1/images";

  constructor(private http: HttpClient) { }

  saveImage(file: File, filename: string): Observable<string> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    formData.append('filename', filename);

    const headers = new HttpHeaders({
      'Accept': 'application/json',
    });

    return this.http.post<string>(ImageService.baseUrlApiImage, formData, { headers });
  }

  getImagesByGroup(group: string): Observable<ImageModel[]> {
    const url: string = `${ImageService.baseUrlApiImage}`;
    return this.http.get<ImageModel[]>(url, {
      params: new HttpParams().append("g", group)
    });
  }
}
