import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  isSidebarClosed: boolean = true;

  constructor() {}

  toggleSidebar() {
    this.isSidebarClosed = !this.isSidebarClosed;
  }

  ngOnInit(): void {

  }

}
