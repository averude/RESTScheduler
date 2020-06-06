import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from './topbar/topbar.component';
import { ShiftOrDepartmentAdmin } from './shift-or-department-admin.module';
import { SimpleNotificationsModule } from "angular2-notifications";
import { StatisticsModule } from "./schedule/components/statistics/statistics.module";
import { ScheduleTabBarComponent } from './schedule/components/schedule-tab-bar/schedule-tab-bar.component';
import { ScheduleGenerationModule } from "./schedule/components/schedule-generation/schedule-generation.module";
import { ReportsModule } from "./reports/reports.module";
import { CalendarModule } from "./schedule/components/calendar/calendar.module";
import { ShiftCompositionModule } from "./schedule/components/shift-composition/shift-composition.module";
import { RemoveDialogModule } from "../../shared/abstract-components/remove-dialog/remove-dialog.module";
import { WorkingTimeModule } from "./schedule/components/working-time/working-time.module";
import { NgxMaskModule } from "ngx-mask";

@NgModule({
  imports: [
    CommonModule,
    CalendarModule,
    ShiftCompositionModule,
    WorkingTimeModule,
    RemoveDialogModule,
    StatisticsModule,
    ScheduleGenerationModule,
    ReportsModule,
    ShiftOrDepartmentAdmin,
    SimpleNotificationsModule,
    NgxMaskModule.forRoot()
  ],
  declarations: [
    TopbarComponent,
    ScheduleTabBarComponent
  ],
  exports: [],
})
export class ShiftOrDepartmentModule {}