import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {
  @Input({required: false}) type: string = "fill";
  @Input({required: false}) theme: string = "dark";
  @Input({required: false}) href: string = "#";
  @Input({required: false}) value: string = "";

  isType(type: string) {
    return this.type.includes(type);
  }

  isTheme(theme: string) {
    return this.theme.includes(theme);
  }

}
