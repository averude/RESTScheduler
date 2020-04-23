import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ContextMenuComponent } from "../../../../../../../../lib/ngx-contextmenu/contextMenu.component";
import { forkJoin, Subscription } from "rxjs";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { ScheduleGenerationService } from "../../../../../../../../services/generators/schedule-generation.service";
import { ClearSelectionService } from "../../../../../../../../lib/ngx-schedule-table/service/clear-selection.service";
import { SelectionEndService } from "../../../../../../../../lib/ngx-schedule-table/service/selection-end.service";
import { ContextMenuService } from "../../../../../../../../lib/ngx-contextmenu/contextMenu.service";
import { CustomDaytypeDialogComponent } from "../custom-daytype-dialog/custom-daytype-dialog.component";
import { SelectionData } from "../../../../../../../../lib/ngx-schedule-table/model/selection-data";
import { DepartmentDayType } from "../../../../../../../../model/department-day-type";
import { ShiftPatternDto } from "../../../../../../../../model/dto/basic-dto";
import { PatternUnit } from "../../../../../../../../model/pattern-unit";
import { ShiftPatternDtoService } from "../../../../../../../../http-services/shift-pattern-dto.service";
import { DepartmentDayTypeService } from "../../../../../../../../http-services/department-day-type.service";

@Component({
  selector: 'app-schedule-table-context-menu',
  templateUrl: './schedule-table-context-menu.component.html',
  styleUrls: ['./schedule-table-context-menu.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScheduleTableContextMenuComponent implements OnInit, OnDestroy {

  @ViewChild(ContextMenuComponent, { static: false })
  patternMenu:  ContextMenuComponent;

  patternDtos:         ShiftPatternDto[]   = [];
  departmentDayTypes:  DepartmentDayType[] = [];

  private selectionEndSub: Subscription;

  constructor(private dialog: MatDialog,
              private cd: ChangeDetectorRef,
              private shiftPatternDtoService: ShiftPatternDtoService,
              private departmentDayTypeService: DepartmentDayTypeService,
              private scheduleGenerationService: ScheduleGenerationService,
              private rowClearSelection: ClearSelectionService,
              private selectionEndService: SelectionEndService,
              private contextMenuService: ContextMenuService) { }

  ngOnInit() {
    forkJoin([this.shiftPatternDtoService.getAllByAuth(),
      this.departmentDayTypeService.getAllByAuth()]
    ).subscribe(values => {
      this.patternDtos = values[0];
      this.departmentDayTypes = values[1];
      this.cd.markForCheck();
    });

    this.selectionEndSub = this.selectionEndService.onSelectionEnd
      .subscribe(selectionData => {
        const selectedCells = selectionData.selectedCells;

        if (selectedCells && selectedCells.length > 0) {
          setTimeout(() => {
            this.contextMenuService.show.next({
              contextMenu: this.patternMenu,
              event: selectionData.event,
              item: selectionData,
            });
            selectionData.event.preventDefault();
            selectionData.event.stopPropagation();
          });
        }
      });
  }

  openCustomDayDialog(data: SelectionData) {
    const config = new MatDialogConfig();
    config.data = this.departmentDayTypes;
    // config.hasBackdrop = false;

    this.dialog.open(CustomDaytypeDialogComponent, config)
      .afterClosed().subscribe(customDay => {
        if (customDay) {
          this.scheduleGenerationService.generateScheduleByPatternUnit(customDay, data);
        }
    });
  }

  getDepDayType(unit: PatternUnit): DepartmentDayType {
    let departmentDayType = this.departmentDayTypes.find(depDayType => depDayType.dayType.id === unit.dayTypeId);
    if (departmentDayType) {
      departmentDayType.startTime       = unit.startTime;
      departmentDayType.endTime         = unit.endTime;
      departmentDayType.breakStartTime  = unit.breakStartTime;
      departmentDayType.breakEndTime    = unit.breakEndTime;
      return departmentDayType;
    }
  }

  ngOnDestroy(): void {
    this.selectionEndSub.unsubscribe();
  }

  clearSelection() {
    this.rowClearSelection.clearSelection();
  }
}
