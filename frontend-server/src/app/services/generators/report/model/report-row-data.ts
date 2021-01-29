import { RowData } from "../../../../lib/ngx-schedule-table/model/data/row-data";
import { CellData } from "../../../../lib/ngx-schedule-table/model/data/cell-data";
import { ReportCellData, ReportHeaderCell } from "./report-cell-data";
import { ReportMarkup } from "./report-markup";
import { DecorationData } from "./decoration-data";

export class ReportData {
  reportMarkup: ReportMarkup;
  headerData: ReportHeaderCell[];
  tableData:  ReportGroupData[];
  decorationData: DecorationData;
}

export class ReportGroupData {
  id: number;
  name: string;
  rows: ReportRowData[];
}

export class ReportRowData implements RowData {
  id: number;
  name: string;
  position: string;
  cellData: CellData[];
  reportCellData?: ReportCellData[];
}
