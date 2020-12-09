import { RowGroupData } from "../../../../../../lib/ngx-schedule-table/model/data/row-group-data";
import { RowData } from "../../../../../../lib/ngx-schedule-table/model/data/row-data";

export class SchedulerRowGroupData implements RowGroupData {
  id: number;
  name: string;
  rows: RowData[];
}
