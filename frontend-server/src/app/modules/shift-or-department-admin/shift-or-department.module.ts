import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShiftOrDepartmentAdminComponent } from './shift-or-department-admin/shift-or-department-admin.component';
import { ShiftOrDepartmentAdmin } from './shift-or-department-admin.module';
import { SimpleNotificationsModule } from "angular2-notifications";
import { StatisticsModule } from "./schedule/components/statistics/statistics.module";
import { ScheduleTabBarComponent } from './schedule/components/schedule-tab-bar/schedule-tab-bar.component';
import { ReportsModule } from "./schedule/components/reports/reports.module";
import { CalendarModule } from "./schedule/components/calendar/calendar.module";
import { MainShiftCompositionModule } from "./schedule/components/main-shift-composition/main-shift-composition.module";
import { RemoveDialogModule } from "../../shared/abstract-components/remove-dialog/remove-dialog.module";
import { WorkingNormModule } from "./schedule/components/working-time/working-norm.module";
import { NgxMaskModule } from "ngx-mask";
import { AvrTopBarModule } from "../../lib/avr-top-bar/avr-top-bar.module";
import { SubstitutionShiftCompositionModule } from "./schedule/components/substitution-shift-composition/substitution-shift-composition.module";

@NgModule({
  imports: [
    CommonModule,
    AvrTopBarModule,
    CalendarModule,
    MainShiftCompositionModule,
    SubstitutionShiftCompositionModule,
    WorkingNormModule,
    RemoveDialogModule,
    StatisticsModule,
    ReportsModule,
    ShiftOrDepartmentAdmin,
    SimpleNotificationsModule,
    NgxMaskModule.forRoot()
  ],
  declarations: [
    ShiftOrDepartmentAdminComponent,
    ScheduleTabBarComponent
  ],
  exports: [],
})
export class ShiftOrDepartmentModule {}