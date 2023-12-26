export class ClientModel {
  firstName: string = "";
  lastName: string = "";
  email: string = "";
  phoneNumber: string = "";

  constructor(partial: Partial<ClientModel>) {
    Object.assign(this, partial);
  }
}

