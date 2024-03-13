import {Component, ViewChild} from '@angular/core';
import {FeedbackModel} from "../../../model/FeedbackModel";
import {CreateFeedbackModel} from "../../components/feedbacks/create-feedback-dialog/CreateFeedbackModel";
import {FeedbackService} from "../../services/feedback.service";
import {ClientService} from "../../services/client.service";

@Component({
  selector: 'app-feedbacks-page',
  templateUrl: './feedbacks-page.component.html',
  styleUrls: ['./feedbacks-page.component.css']
})
export class FeedbacksPageComponent {
  feedbacks: FeedbackModel[] = [];
  isDialogVisible: boolean = false;

  constructor(
    private feedbackService: FeedbackService,
    private clientService: ClientService
  ) {
    this.refreshFeedbacks();
  }

  refreshFeedbacks() {
    this.feedbackService.getAllFeedbacks().subscribe(data => {
      this.feedbacks = data.content;
      console.log(this.feedbacks);
    });
  }

  openDialog() {
    this.isDialogVisible = true;
  }

  onDialogClose() {
    this.isDialogVisible = false;
  }

  onDialogSubmit(reviewData: CreateFeedbackModel) {
    let feedback: FeedbackModel | undefined;

    this.clientService.getByEmail(reviewData.email).subscribe(clientModel => {
      this.feedbackService.create(new FeedbackModel({
        author: clientModel,
        rating: reviewData.rating,
        text: reviewData.text
      })).subscribe(data => {
        feedback = data;
      });
    });
    this.refreshFeedbacks();
  }

  getDataString(date: Date): string {
    const dateParts = date.toLocaleString("uk_UA").split("T");
    return dateParts[0].replaceAll("-", ".");
  }
}
