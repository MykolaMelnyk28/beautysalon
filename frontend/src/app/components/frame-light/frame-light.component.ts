import {Component, Input} from '@angular/core';

@Component({
  selector: 'frame-light',
  templateUrl: './frame-light.component.html',
  styleUrls: ['./frame-light.component.css']
})
export class FrameLightComponent {
  @Input() hr: string = "hug";
  @Input() vr: string = "hug";

  constructor() {

  }

  getClassList(): string {
    let classes = "frame-light";

    switch (this.hr) {
      case "hug":
        classes += " hr-hug";
        break;
      case "fill":
        classes += " hr-fill";
        break;
    }

    switch (this.vr) {
      case "hub":
        classes += " vr-hug";
        break;
      case "fill":
        classes += " vr-hug";
        break;
    }

    return classes;
  }

}
