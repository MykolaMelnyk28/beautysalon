import {ClientModel} from "./ClientModel";

export interface FeedbackModel {
  author: ClientModel;
  dateCreated: Date;
  rating: number;
  text: string;
}
