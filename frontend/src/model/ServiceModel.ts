
export class ServiceModel {
  name: string = "";
  category: string = "";
  description: string = "";
  durationInMinute: number = 0;
  price: number = 0;
  currency: string = "";

  constructor(partial: Partial<ServiceModel>) {
    Object.assign(this, partial);
  }

}
