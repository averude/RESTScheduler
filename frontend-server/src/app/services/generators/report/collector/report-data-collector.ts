import { CalendarDay } from "../../../../lib/ngx-schedule-table/model/calendar-day";
import { DayType } from "../../../../model/day-type";
import { BasicDto } from "../../../../model/dto/basic-dto";
import { Employee } from "../../../../model/employee";
import { WorkDay } from "../../../../model/workday";
import { SummationDto } from "../../../../model/dto/summation-dto";
import { MainShiftComposition } from "../../../../model/main-shift-composition";
import { ReportRowData } from "../model/report-row-data";

export interface ReportDataCollector {
  collect(dates: CalendarDay[],
          dayTypes: DayType[],
          schedule: BasicDto<Employee, WorkDay>[],
          summations: SummationDto[],
          compositions: MainShiftComposition[]): ReportRowData[];

  REPORT_TYPE: string;
}