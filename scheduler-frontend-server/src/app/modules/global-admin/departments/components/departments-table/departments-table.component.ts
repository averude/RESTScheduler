import { Component } from '@angular/core';
import { TableBaseComponent } from "../../../../../shared/abstract-components/table-base/table-base.component";
import { Department } from "../../../../../model/department";
import { MatDialog } from "@angular/material";
import { DepartmentService } from "../../../../../http-services/department.service";
import { NotificationsService } from "angular2-notifications";
import { DepartmentDialogComponent } from "../department-dialog/department-dialog.component";

@Component({
  selector: 'app-departments-table',
  templateUrl: './departments-table.component.html',
  styleUrls: ['../../../../../shared/common/table.common.css', './departments-table.component.css']
})
export class DepartmentsTableComponent extends TableBaseComponent<Department> {
  displayedColumns = ['select', 'name', 'control'];

  constructor(dialog: MatDialog,
              departmentService: DepartmentService,
              notificationsService: NotificationsService) {
    super(dialog, departmentService, notificationsService);
  }

  ngOnInit() {
    super.ngOnInit();
  }

  openDialog(department: Department) {
    const data = {
      department: department
    };

    this.openAddOrEditDialog(department, data, DepartmentDialogComponent);
  }
}
