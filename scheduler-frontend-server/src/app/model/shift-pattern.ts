import { IdEntity } from "./interface/id-entity";

export class ShiftPattern implements IdEntity {
  id:                     number;
  name:                   string;
  departmentId:           number;
  holidayDayTypeId:       number;
  extraWeekendDayTypeId:  number;
  extraWorkDayDayTypeId:  number;
}