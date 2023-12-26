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

  isDialogVisible = false;
  selectedService = new ServiceModel({});
  treeRoot: TreeNode | null;

  constructor(private serviceService: ServiceEntityService) {
    console.log(this.serviceService.getTree());
    this.treeRoot = this.serviceService.getTree();
  }

  hasContent(): boolean {
    return !!this.treeRoot;
  }

  onLeafNodeClick(leafNode: TreeNode) {
    const foundService = this.serviceService.findByNode(leafNode);
    this.serviceService.findByNode(leafNode).subscribe(foundService => {
      this.selectedService = Object.assign(new ServiceModel({}), foundService);
      this.openServiceDialog();
    });
    // this.selectedService = foundService || new ServiceModel({});
    // this.openServiceDialog();
  }

  openServiceDialog() {
    this.isDialogVisible = true;
  }

  onDialogClose() {
    this.isDialogVisible = false;
  }
}
