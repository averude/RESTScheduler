import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  ViewChild,
} from '@angular/core';
import { TableRenderer } from "../service/table-renderer.service";
import { Subscription } from "rxjs";
import { filter } from "rxjs/operators";
import { ClearSelectionService } from "../service/clear-selection.service";
import { SelectableRowDirective } from "../directives/selectable-row.directive";
import { Row } from "../model/data/row";
import { Cell } from "../model/data/cell";
import { PaginationService } from "../service/pagination.service";
import { SelectionEndService } from "../service/selection-end.service";
import { AfterDateColumnDef, BeforeDateColumnDef, PageableColumnDef } from "../directives/column";
import { Options } from "../model/options";

@Component({
  selector: '[app-table-row]',
  templateUrl: './table-row.component.html',
  styleUrls: ['./table-row.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TableRowComponent implements OnInit, OnDestroy {

  @Input() trackByFn;
  @Input() options: Options;

  @Input() pageableColumns:   PageableColumnDef;
  @Input() beforeDateColumns: QueryList<BeforeDateColumnDef>;
  @Input() afterDateColumns:  QueryList<AfterDateColumnDef>;

  @Input() rowGroupId:      number;
  @Input() rowData:         Row;

  @ViewChild(SelectableRowDirective, { static: true })
  selectableRowDirective: SelectableRowDirective;

  private rowRenderSub:     Subscription;
  private rowClearSub:      Subscription;

  constructor(public elementRef: ElementRef,
              private paginatorService: PaginationService,
              private cd: ChangeDetectorRef,
              private tableRenderer: TableRenderer,
              private selectionEndService: SelectionEndService,
              private rowClearSelection: ClearSelectionService) { }

  ngOnInit() {
    this.rowRenderSub = this.tableRenderer.onRenderRow
      .pipe(filter(rowDataId => this.rowData.id === rowDataId))
      .subscribe(() => this.renderCells());

    this.rowClearSub = this.rowClearSelection.onClearSelection()
      .subscribe(() => this.clearSelection());
  }

  ngOnDestroy(): void {
    this.rowRenderSub.unsubscribe();
    this.rowClearSub.unsubscribe();
  }

  onSelectionEnd($event: MouseEvent,
                 selectedCells: Cell[]): void {
    this.selectionEndService.endSelection($event, this.rowData, selectedCells);
  }

  clearSelection() {
    this.selectableRowDirective.clearSelection();
  }

  renderCells() {
    if (!this.rowData) {
      return;
    }

    this.cd.detectChanges();
  }

  getContentPositioning() {
    if (this.pageableColumns && this.pageableColumns.cellContentPosition) {

      switch (this.pageableColumns.cellContentPosition) {
        case 'TOP' : return 'content-top-position';
        case 'MIDDLE' : return 'content-middle-position';
        default: return 'content-middle-position';
      }

    } else {
      return 'content-middle-position';
    }
  }
}
