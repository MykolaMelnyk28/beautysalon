import {Component, OnInit} from '@angular/core';
import {ServiceModel} from "../../../model/ServiceModel";
import {ServiceEntityService} from "../../services/service-entity.service";
import {TreeNode} from "../../../model/TreeNode";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  services: ServiceModel[] = [];
  treeRoot!: TreeNode | null;

  constructor(private serviceService: ServiceEntityService) {}

  ngOnInit(): void {
    this.serviceService.getAllServices().subscribe(data => {
      this.services = data;
    });
    this.treeRoot = this.serviceService.getTree();
  }

  hasContent(): boolean {
    return this.treeRoot !== null && this.services.length > 0;
  }

  getRootCategories(): string[] {
    return this.treeRoot?.children.map(node => node.name) || [];
  }
}
