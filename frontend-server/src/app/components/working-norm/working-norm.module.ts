import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "../../shared/shared.module";
import { ScheduleTableModule } from "../../lib/ngx-schedule-table/schedule-table.module";
import { MonthYearPaginatorModule } from "../../shared/paginators/month-year-paginator/month-year-paginator.module";
import { YearPaginationStrategy } from "../../shared/paginators/pagination-strategy/year-pagination-strategy";
import { WorkingNormTableDataCollector } from "./collector/working-norm-table-data-collector";
import { WorkingNormTableComponent } from "./working-norm-table/working-norm-table.component";
import { WorkingNormDialogComponent } from './working-norm-dialog/working-norm-dialog.component';
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { AvrEntityGenerationModule } from "../../lib/avr-entity-generation/avr-entity-generation.module";
import { TableSumCalculator } from "../../services/calculators/table-sum-calculator.service";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { WorkingNormTableConfigurationMenuModule } from "./working-norm-table-configuration-menu/working-norm-table-configuration-menu.module";
import { NameInfoCellModule } from "../shared/name-info-cell/name-info-cell.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    AvrEntityGenerationModule,
    ReactiveFormsModule,
    ScheduleTableModule,
    WorkingNormTableConfigurationMenuModule,
    MonthYearPaginatorModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    NameInfoCellModule
  ],
  declarations: [
    WorkingNormTableComponent,
    WorkingNormDialogComponent
  ],
  providers: [
    YearPaginationStrategy,
    WorkingNormTableDataCollector,
    TableSumCalculator
  ]
})
export class WorkingNormModule {}
