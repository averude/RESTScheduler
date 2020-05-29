import { IdEntity } from "./interface/id-entity";
import { DepartmentDayType } from "./department-day-type";

export class ShiftPattern implements IdEntity {
  id:                     number;
  name:                   string;
  // departmentId:           number;
  holidayDepDayType:      DepartmentDayType;
  extraWeekendDepDayType: DepartmentDayType;
  extraWorkDayDepDayType: DepartmentDayType;
}
