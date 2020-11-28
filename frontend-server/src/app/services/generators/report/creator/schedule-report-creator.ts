import { Cell, Row, Worksheet } from "exceljs";
import { ReportRowData } from "../model/report-row-data";
import { SummationColumn } from "../../../../model/summation-column";
import {
  arialCyrSize10,
  arialCyrSize8,
  centerAlignVertRotation,
  centerMiddleAlign,
  holidayFill,
  leftRightBottomMediumBorders,
  leftRightMediumBorders,
  leftRightThinBottomMediumBorders,
  mediumBorders,
  topLeftRightMediumBorders,
  topMediumBorders,
  topMediumLeftRightThinBorders,
  weekendFill
} from "../styles/report-styles";
import { CalendarDay } from "../../../../lib/ngx-schedule-table/model/calendar-day";
import { ReportCreator } from "./report-creator";
import { SCHEDULE_REPORT } from "../model/report-types";
import { CellFiller } from "../core/cell-filler";
import { AReportCreator } from "./a-report-creator";
import { ReportMarkup } from "../model/report-markup";

export class ScheduleReportCreator extends AReportCreator implements ReportCreator {
  REPORT_TYPE: string = SCHEDULE_REPORT;

  constructor(cellFiller: CellFiller) {
    super(cellFiller);
  }

  styleColumns(sheet: Worksheet,
               daysInMonth: number,
               summationColumnsCount: number,
               reportMarkup: ReportMarkup) {
    let col_idx = reportMarkup.col_start_num;
    let idCol = sheet.getColumn(col_idx++);
    idCol.key = 'id';
    idCol.width = 3;

    let nameCol = sheet.getColumn(col_idx++);
    nameCol.key = 'name';
    nameCol.width = 20;

    let positionCol = sheet.getColumn(col_idx++);
    positionCol.key = 'position';
    positionCol.width = 8;

    for (let day_idx = 1; day_idx <= daysInMonth; col_idx++, day_idx++) {
      let col = sheet.getColumn(col_idx);
      col.key = `day_${day_idx}`;
      col.width = 5;
    }

    for (let sum_col_idx = 0; sum_col_idx < summationColumnsCount; sum_col_idx++, col_idx++) {
      let col = sheet.getColumn(col_idx);
      col.key = `sum_col_${col_idx}`;
    }
  }

  createHeader(sheet: Worksheet,
               data: ReportRowData[],
               summationColumns: SummationColumn[],
               calendarDays: CalendarDay[],
               reportMarkup: ReportMarkup) {
    let last_header_row_number = reportMarkup.row_start_num + reportMarkup.header_height;
    let col_idx = reportMarkup.col_start_num;

    const rows: Row[] = [];
    for (let i = reportMarkup.row_start_num; i <= last_header_row_number; i++) {
      rows.push(sheet.getRow(i));
    }

    rows[1].height = 35;

    sheet.mergeCells(reportMarkup.row_start_num, col_idx, last_header_row_number, col_idx);
    const idCell = rows[0].getCell(col_idx++);
    idCell.value = '№';
    idCell.style.font = arialCyrSize10;
    idCell.style.border = mediumBorders;
    idCell.style.alignment = centerMiddleAlign;

    sheet.mergeCells(reportMarkup.row_start_num, col_idx, last_header_row_number, col_idx);
    const nameCell = rows[0].getCell(col_idx++);
    nameCell.value = 'П.І.Б.';
    nameCell.style.font = arialCyrSize10;
    nameCell.style.border = mediumBorders;
    nameCell.style.alignment = centerMiddleAlign;

    const posCellIdx = col_idx++;
    const posCell = rows[1].getCell(posCellIdx);
    posCell.value = 'Посада';
    posCell.style.font = arialCyrSize10;
    rows[0].getCell(posCellIdx).style.border = topLeftRightMediumBorders;
    posCell.style.border = leftRightMediumBorders;
    rows[2].getCell(posCellIdx).style.border = leftRightBottomMediumBorders;
    posCell.style.alignment = centerAlignVertRotation;

    let monthDateCaption = rows[0].getCell(col_idx);
    monthDateCaption.value = 'Числа місяця';
    monthDateCaption.style.font = arialCyrSize10;

    for (let day_idx = 0; day_idx < calendarDays.length; day_idx++, col_idx++) {
      const dateCell = rows[2].getCell(col_idx);
      const calendarDay = calendarDays[day_idx];
      dateCell.value = day_idx + 1;
      dateCell.style.font = arialCyrSize10;
      dateCell.style.border = leftRightThinBottomMediumBorders;
      rows[0].getCell(col_idx).style.border = topMediumBorders;
      const colorLabelCell = rows[1].getCell(col_idx);
      colorLabelCell.style.border = topMediumLeftRightThinBorders;
      this.setFill(calendarDay, colorLabelCell);
    }

    for (let sum_col_idx = 0; sum_col_idx < summationColumns.length; sum_col_idx++, col_idx++) {
      sheet.mergeCells(reportMarkup.row_start_num, col_idx, last_header_row_number, col_idx);
      const summationColumn = summationColumns[sum_col_idx];
      const sumColCell = rows[0].getCell(col_idx);
      sumColCell.value = summationColumn.name;
      sumColCell.style.font = arialCyrSize8;
      sumColCell.style.border = mediumBorders;
      sumColCell.style.alignment = centerAlignVertRotation;
    }

    rows.forEach(row => row.commit());
  }

  private setFill(date: CalendarDay, cell: Cell) {
    if (date.weekend) {
      cell.style.fill = weekendFill;
    } else if (date.holiday) {
      cell.style.fill = holidayFill;
    }
  }
}
