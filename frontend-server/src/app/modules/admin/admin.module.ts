import { NgModule } from "@angular/core";
import { AdminComponent } from './admin.component';
import { CommonModule } from "@angular/common";
import { AdminRoutingModule } from "./admin-routing.module";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatSidenavModule } from "@angular/material/sidenav";
import { CellCollector } from "../../services/collectors/cell-collector";
import { CellUpdater } from "../../services/collectors/cell-updater";
import { CalendarModule } from "../../components/calendar/calendar.module";
import { StatisticsModule } from "../../components/statistics/statistics.module";
import { WorkingNormModule } from "../../components/working-norm/working-norm.module";
import { ReportsModule } from "../../components/report-generator/reports.module";
import { SimpleNotificationsModule } from "angular2-notifications";
import { MatMenuModule } from "@angular/material/menu";
import { ChangeUserAccountPasswordDialogModule } from "../../components/change-user-account-password-dialog/change-user-account-password-dialog.module";
import { NgxMaskModule } from "ngx-mask";
import { SidePanelStepperComponent } from '../../lib/side-panel-stepper/side-panel-stepper.component';
import { CdkStepperModule } from "@angular/cdk/stepper";
import { MatExpansionModule } from "@angular/material/expansion";
import { SimpleAccordionModule } from "../../lib/simple-accordion/simple-accordion.module";
import { ToolbarTemplateService } from "../../services/top-bar/toolbar-template.service";
import { ToolbarRowComponent } from './toolbar-row.component';
import { UserInfoComponent } from './user-info/user-info.component';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatToolbarModule,
    MatSidenavModule,
    MatMenuModule,
    MatExpansionModule,
    CalendarModule,
    StatisticsModule,
    WorkingNormModule,
    ReportsModule,
    CdkStepperModule,
    SimpleAccordionModule,
    ChangeUserAccountPasswordDialogModule,
    SimpleNotificationsModule,
    NgxMaskModule.forRoot()
  ],
  declarations: [
    AdminComponent,
    SidePanelStepperComponent,
    ToolbarRowComponent,
    UserInfoComponent
  ],
  entryComponents: [SidePanelStepperComponent],
  providers: [
    CellCollector,
    CellUpdater,
    ToolbarTemplateService
  ]
})
export class AdminModule {
}
