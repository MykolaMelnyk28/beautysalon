import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ImageModel} from "../../model/ImageModel";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  static baseUrlApiImage: string = "http://localhost:8080/api/v1/images";

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
    let url: string = `${ImageService.baseUrlApiImage}/${group}/`;
    return this.http.get<ImageModel[]>(url);
  }
}
