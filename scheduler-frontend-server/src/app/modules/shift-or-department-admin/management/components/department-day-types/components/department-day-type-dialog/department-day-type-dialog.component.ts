import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from "@angular/forms";
import { AuthService } from "../../../../../../../services/http/auth.service";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { DialogBaseComponent } from "../../../../../../../shared/abstract-components/dialog-base/dialog-base.component";
import { DepartmentDayType } from "../../../../../../../model/department-day-type";
import { timeValidationPattern } from "../../../../../../../shared/utils/time-converter";
import { DayType } from "../../../../../../../model/day-type";

@Component({
  selector: 'app-department-day-type-dialog',
  templateUrl: './department-day-type-dialog.component.html',
  styleUrls: ['../../../../../../../shared/common/dialog.common.css', './department-day-type-dialog.component.css']
})
export class DepartmentDayTypeDialogComponent extends DialogBaseComponent<DepartmentDayType> {
  dayTypes: DayType[] = [];

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private dialogRef: MatDialogRef<DepartmentDayTypeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) {
    super(data.departmentDayType, dialogRef);
    this.dayTypes = data.dayTypes;
  }

  initTheForm() {
    this.dialogForm = this.fb.group({
      id:               [],
      // departmentId:     [this.authService.departmentId],
      dayType:          [null,  [Validators.required]],
      startTime:        [null,  [Validators.pattern(timeValidationPattern)]],
      endTime:          [null,  [Validators.pattern(timeValidationPattern)]],
      breakStartTime:   [null,  [Validators.pattern(timeValidationPattern)]],
      breakEndTime:     [null,  [Validators.pattern(timeValidationPattern)]]
    });
  }

  fillInTheForm(departmentDayType: DepartmentDayType) {
    this.dialogForm.setValue({
      id:               departmentDayType.id,
      // departmentId:     departmentDayType.departmentId,
      dayType:          departmentDayType.dayType,
      startTime:        departmentDayType.startTime,
      endTime:          departmentDayType.endTime,
      breakStartTime:   departmentDayType.breakStartTime,
      breakEndTime:     departmentDayType.breakEndTime
    });
  }
}