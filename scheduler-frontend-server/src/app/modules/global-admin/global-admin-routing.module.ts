import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { GlobalAdminComponent } from "./global-admin/global-admin.component";
import { DepartmentsTableComponent } from "./departments/components/departments-table/departments-table.component";
import { UserAccountsTableComponent } from "./accounts/components/user-accounts-table/user-accounts-table.component";
import { DayTypeGroupsTableComponent } from "./day-type-groups/components/day-type-groups-table/day-type-groups-table.component";

const routes = [
  {
    path: '',
    component: GlobalAdminComponent,
    children: [
      {
        path: 'departments',
        component: DepartmentsTableComponent
      },
      {
        path: 'accounts',
        component: UserAccountsTableComponent
      },
      {
        path: 'groups',
        component: DayTypeGroupsTableComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GlobalAdminRoutingModule {}