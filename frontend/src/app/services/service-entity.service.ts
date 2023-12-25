import { Injectable } from '@angular/core';
import {TreeNode} from "../../model/TreeNode";
import {ServiceModel} from "../../model/ServiceModel";

@Injectable({
  providedIn: 'root'
})
export class ServiceEntityService {

  services: ServiceModel[];

  constructor() {
    this.services = [];
  }

  findByNode(serviceNode: TreeNode): ServiceModel | undefined {
    return this.services.find(s => s.name == serviceNode.name);
  }

  getAllServices(): Set<ServiceModel> {
    return new Set(this.services);
  }

  getTree(): TreeNode | null {
    let root: TreeNode = new TreeNode("_");

    let services = this.getAllServices();

    services.forEach(service => {
      const categories = service.category.split('.');
      let currentNode = root;

      categories.forEach(category => {
        let childNode = currentNode.children.find(node => node.name === category);

        if (!childNode) {
          childNode = new TreeNode(category);
          currentNode.children.push(childNode);
        }

        currentNode = childNode;
      });

      currentNode.children.push(new TreeNode(service.name));
    });

    return (root.children.length > 0) ? root : null;
  }
}
