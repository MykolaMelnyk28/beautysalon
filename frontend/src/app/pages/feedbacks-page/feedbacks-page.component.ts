import {Component, ViewChild} from '@angular/core';
import {FeedbackModel} from "../../../model/FeedbackModel";
import {CreateFeedbackModel} from "../../components/feedbacks/create-feedback-dialog/CreateFeedbackModel";
import {ClientModel} from "../../../model/ClientModel";

@Component({
  selector: 'app-feedbacks-page',
  templateUrl: './feedbacks-page.component.html',
  styleUrls: ['./feedbacks-page.component.css']
})
export class FeedbacksPageComponent {
  feedbacks: FeedbackModel[];

  isDialogVisible: boolean = false;

  constructor() {
    this.feedbacks = [
      {
        text: "Відгук 1",
        rating: 1,
        dateCreated: new Date(),
        author: new ClientModel({
          firstName: "Прізвище1",
          lastName: "Ім'я1",
          email: "email1@email.com"
        })
      },
      {
        text: "Відгук 2",
        rating: 5,
        dateCreated: new Date(),
        author: new ClientModel({
          firstName: "Прізвище2",
          lastName: "Ім'я2",
          email: "email2@email.com"
        })
      }
    ];
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
