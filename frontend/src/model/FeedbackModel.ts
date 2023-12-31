import {ClientModel} from "./ClientModel";

export class FeedbackModel {
  author: ClientModel = new ClientModel({});
  dateCreated: Date = new Date();
  rating: number = 1;
  text: string = "";

  constructor(partial: Partial<FeedbackModel>) {
    Object.assign(this, partial);
  }
}
