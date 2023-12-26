import {Component, OnInit} from '@angular/core';
import {TreeNode} from "../../../model/TreeNode";
import {EmployeeModel} from "../../../model/EmployeeModel";
import {ServiceModel} from "../../../model/ServiceModel";
import {ServiceEntityService} from "../../services/service-entity.service";

@Component({
  selector: 'app-services-page',
  templateUrl: './services-page.component.html',
  styleUrls: ['./services-page.component.css']
})
export class ServicesPageComponent {
  isDialogVisible: boolean = false;
  selectedService: ServiceModel;

  treeRoot: TreeNode | null;

  constructor(
    private serviceService: ServiceEntityService
  ) {

    this.selectedService = new ServiceModel({});
    this.treeRoot = this.serviceService.getTree();
  }

  hasContent(): boolean {
    return this.treeRoot !== null;
  }

  onLeafNodeClick(leafNode: TreeNode) {
    let s: any = this.serviceService.findByNode(leafNode);
    if (s) {
      this.selectedService = s;
    } else {
      this.selectedService = new ServiceModel({});
    }
    this.openServiceDialog();
  }

  openServiceDialog() {
    this.isDialogVisible = true;
  }

  onDialogClose() {
    this.isDialogVisible = false;
  }
}
