import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CreateFeedbackModel} from "./CreateFeedbackModel";

@Component({
  selector: 'app-create-feedback-dialog',
  templateUrl: './create-feedback-dialog.component.html',
  styleUrls: ['./create-feedback-dialog.component.css']
})
export class CreateFeedbackDialogComponent {
  @Input() isVisible: boolean = false;
  @Input() title: string = '';
  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<any>();

  feedback: CreateFeedbackModel = {
    email: "",
    rating: 1,
    text: ""
  };

  onSubmit() {
    this.submit.emit(this.feedback);
    this.onClose();
  }

  onClose() {
    this.isVisible = false;
    this.close.emit();
  }
}
