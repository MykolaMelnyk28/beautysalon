import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {
  @Input() type?: string = "fill";
  @Input() theme?: string = "dark";
  @Input() href?: string = "";
  @Input() value?: string = "";

  constructor(private router: Router) {}

  isType(type: string): boolean {
    return this.type === type;
  }

  isTheme(theme: string): boolean {
    return this.theme === theme;
  }

  getRouterLink(): string | null {
    return this.href !== undefined && this.href !== null && this.href !== '' ? this.href : null;
  }

  handleClick() {
    if (this.href && this.href !== "") {
      this.router.navigateByUrl(this.href);
    }
  }
}
