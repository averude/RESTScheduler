import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material';
import { ManagementComponent } from './management/management.component';
import { ManagementRoutingModule } from './management-routing.module';
import { EmployeesModule } from './components/employees/employees.module';
import { PositionsModule } from './components/positions/positions.module';
import { DayTypesModule } from './components/daytypes/daytypes.module';
import { ShiftsModule } from './components/shifts/shifts.module';
import { PatternsModule } from './components/patterns/patterns.module';
import { RemoveDialogModule } from "../../../shared/abstract-components/remove-dialog/remove-dialog.module";
import { HolidaysModule } from "./components/holidays/holidays.module";
import { WorkingTimeModule } from "./components/working-time/working-time.module";
import { ExtraWeekendsModule } from "./components/extra-weekends/extra-weekends.module";
import { ShiftCompositionModule } from "./components/shift-composition/shift-composition.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    EmployeesModule,
    PositionsModule,
    DayTypesModule,
    ShiftsModule,
    ShiftCompositionModule,
    PatternsModule,
    HolidaysModule,
    WorkingTimeModule,
    ExtraWeekendsModule,
    MatSidenavModule,
    ManagementRoutingModule,
    RemoveDialogModule
  ],
  declarations: [
    ManagementComponent
  ],
  providers: []
})
export class ManagementModule {}
