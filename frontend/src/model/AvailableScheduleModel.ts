
export class AvailableScheduleModel {
  startDatetime: Date | undefined;
  endDatetime: Date | undefined;
  email: string = "";

  constructor(partial: Partial<AvailableScheduleModel>) {
    Object.assign(this, partial);
  }
}
