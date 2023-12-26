import { Component } from '@angular/core';

@Component({
  selector: 'app-gallery-page',
  templateUrl: './gallery-page.component.html',
  styleUrls: ['./gallery-page.component.css']
})
export class GalleryPageComponent {
  images = [
    { url: 'path/to/image1.jpg', alt: 'Image 1' },
    { url: 'path/to/image2.jpg', alt: 'Image 2' }
  ];

}
