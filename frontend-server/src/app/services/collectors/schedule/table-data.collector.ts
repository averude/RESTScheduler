import { WorkingNorm } from "../../../model/working-norm";
import * as moment from "moment";
import { ScheduleRow, ScheduleRowGroup, TableData } from "../../../model/ui/schedule-table/table-data";
import { Injectable } from "@angular/core";
import { IntervalCreator } from "../../creator/interval-creator.service";
import { binarySearch } from "../../../shared/utils/collection-utils";
import { TableRowProcessor } from "./table-row-processor.service";
import { InitialData } from "../../../model/datasource/initial-data";
import { convertCompositionToInterval } from "../../../model/ui/schedule-table/row-interval";
import { CellEnabledSetter } from "./cell-enabled-setter";
import { TableSumCalculator } from "../../calculators/table-sum-calculator.service";

@Injectable()
export class TableDataCollector {

  constructor(private intervalCreator: IntervalCreator,
              private cellEnabledSetter: CellEnabledSetter,
              private sumCalculator: TableSumCalculator,
              private rowProcessor: TableRowProcessor) {}

  handleData(initData: InitialData) {
    const data = this.collect(initData);

    data.groups.forEach(group =>
      group.rows.forEach((row: ScheduleRow) => {

        if (row.isSubstitution) {
          row.intervals = row.compositions.map(composition => convertCompositionToInterval(composition));
        } else {
          const dto = binarySearch(initData.scheduleDTOs, (mid => mid.parent.id - row.id));
          row.intervals = this.intervalCreator.getEmployeeShiftIntervalsByArr(row.compositions, dto.substitutionCompositions);
        }

        this.cellEnabledSetter.processRow(row, data.from, data.to);
      }));

    const rowGroupData = data.groups;

    this.sumCalculator.calculateWorkHoursSum(rowGroupData);
    return rowGroupData;
  }

  collect(initialData: InitialData) {
    const table: TableData = new TableData();

    table.from  = moment.utc(initialData.calendarDays[0].isoString);
    table.to    = moment.utc(initialData.calendarDays[initialData.calendarDays.length - 1].isoString);

    table.groups = initialData.shifts
      .sort((a, b) => a.id - b.id)
      .map(shift => {
        const group = new ScheduleRowGroup();
        group.table = table;
        group.id    = shift.id;
        group.name  = shift.name;
        group.rows  = [];
        return group;
      });

    for (let dto of initialData.scheduleDTOs) {

      this.rowProcessor.fillRows(initialData, dto, dto.mainCompositions, false,
        (composition => table.findRowGroup(composition.shiftId)),
        (composition => this.getWorkingNorm(initialData.workingNorms, composition.shiftId)));

      this.rowProcessor.fillRows(initialData, dto, dto.substitutionCompositions, true,
        (composition => table.findRowGroup(composition.shiftId)),
        (composition => this.getWorkingNorm(initialData.workingNorms, composition.mainComposition.shiftId)))
    }

    return table;
  }

  private getWorkingNorm(workingNorms: WorkingNorm[], shiftId: number) {
    return binarySearch(workingNorms, (mid => mid.shiftId - shiftId))?.hours || 0;
  }
}
