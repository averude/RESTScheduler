import { ScheduleRow, TableData } from "../../../model/ui/schedule-table/table-data";
import { Moment } from "moment";
import { Injectable } from "@angular/core";
import { RowInterval } from "../../../model/ui/schedule-table/row-interval";

@Injectable()
export class CellEnabledSetter {

  processCells<T>(cells: T[],
                  intervals: RowInterval[],
                  from: Moment,
                  to: Moment,
                  cellFn: (cell: T) => void) {

    if (!cells || !intervals || cells.length === 0 || from.isAfter(to)) {
      return;
    }

    intervals.forEach(interval => {
      const begin = from.isSameOrAfter(interval.from) ? from : interval.from;
      const end   = to.isSameOrBefore(interval.to) ? to : interval.to;

      if (begin.isSameOrBefore(end)) {
        let start_idx = begin.date() - 1;
        let end_idx   = end.date();

        for (let i = start_idx; i < end_idx; i++) {
          cellFn(cells[i]);
        }
      }
    });
  }

  processRow(row: ScheduleRow, from: Moment, to: Moment) {
    row.cells.forEach(cell => cell.enabled = false);

    let count = row.cells.length;

    this.processCells(row.cells, row.intervals, from, to,
      (cell => {
        count--;
        cell.enabled = true
      })
    );

    row.hidden = (count === row.cells.length);
  }

  process(row: ScheduleRow) {
    this.processRow(row, row.group.table.from, row.group.table.to);
  }

  processTable(table: TableData) {
    table.groups.forEach(group => {
      group.rows.forEach((row: ScheduleRow) => this.processRow(row, table.from, table.to));
    });
  }
}
