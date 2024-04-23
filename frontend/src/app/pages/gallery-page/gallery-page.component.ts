import { Component } from '@angular/core';
import {ImageModel} from "../../../model/ImageModel";
import {ImageService} from "../../services/image.service";

@Component({
  selector: 'app-gallery-page',
  templateUrl: './gallery-page.component.html',
  styleUrls: ['./gallery-page.component.css']
})
export class GalleryPageComponent {
  images: ImageModel[] = [];
  selectedImage: string = "";

  constructor(
    private imageService: ImageService
  ) {
    this.imageService.getImagesByGroup('').subscribe(
        data => this.images = data,
        error => console.error(error)
    );
  }

  handleImageClick(image: ImageModel) {
      this.selectedImage = image.url;
  }
}
