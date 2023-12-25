import {Component, ViewChild} from '@angular/core';
import {FeedbackModel} from "../../../model/FeedbackModel";
import {CreateFeedbackModel} from "../../components/feedbacks/create-feedback-dialog/CreateFeedbackModel";

@Component({
  selector: 'app-feedbacks-page',
  templateUrl: './feedbacks-page.component.html',
  styleUrls: ['./feedbacks-page.component.css']
})
export class FeedbacksPageComponent {
  feedbacks: FeedbackModel[];

  isDialogVisible: boolean = false;

  constructor() {
    this.feedbacks = [];
  }

  openDialog() {
    this.isDialogVisible = true;
  }

  onDialogClose() {
    this.isDialogVisible = false;
  }

  onDialogSubmit(reviewData: CreateFeedbackModel) {
    //let f = {};
    //this.feedbacks.push(f);
  }
}
