import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EmployeeModel} from "../../../../model/EmployeeModel";
import {ServiceModel} from "../../../../model/ServiceModel";

@Component({
  selector: 'app-view-service-dialog',
  templateUrl: './view-service-dialog.component.html',
  styleUrls: ['./view-service-dialog.component.css']
})
export class ViewServiceDialogComponent {
  @Input() isVisible: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Input() item: ServiceModel = new ServiceModel({});

  onClose() {
    this.isVisible = false;
    this.close.emit();
  }
}
