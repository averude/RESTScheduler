import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../../http-services/auth.service";

@Component({
  selector: 'app-global-admin',
  templateUrl: './global-admin.component.html',
  styleUrls: ['./global-admin.component.css',
    '../../../shared/common/sidebar.common.css',
    '../../../shared/common/toolbar.common.css']
})
export class GlobalAdminComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  logout() {
    this.authService.logout();
  }
}