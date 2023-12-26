import { Component } from '@angular/core';
import {ServiceModel} from "../../../model/ServiceModel";
import {ServiceEntityService} from "../../services/service-entity.service";
import {TreeNode} from "../../../model/TreeNode";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {

  services: Set<ServiceModel>;
  treeRoot: TreeNode | null;

  constructor(
    private serviceService: ServiceEntityService
  ) {
    this.treeRoot = this.serviceService.getTree();
    this.services = serviceService.getAllServices();
  }

  hasContent(): boolean {
    return this.treeRoot !== null;
  }

  getRootCategories(): string[] {
    let nodes: TreeNode[] = this.treeRoot?.children ?? [];
    return nodes.map(x => x.name);
  }
}
