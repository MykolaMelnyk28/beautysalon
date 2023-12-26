import { Injectable } from '@angular/core';
import {TreeNode} from "../../model/TreeNode";
import {ServiceModel} from "../../model/ServiceModel";
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceEntityService {

  static baseUrlApiServices: string = "http://localhost:8080/api/v1/services";

  constructor(private http: HttpClient) {}

  findByNode(serviceNode: TreeNode): Observable<ServiceModel | undefined> {
    return this.getAllServices().pipe(
        map(data => data.find(s => s.name === serviceNode.name))
    );
  }

  getAllServices(): Observable<ServiceModel[]> {
    let url: string = `${ServiceEntityService.baseUrlApiServices}`;
    return this.http.get<ServiceModel[]>(url);
  }

  getTree(): TreeNode | null {
    let root: TreeNode = new TreeNode("_");

    let services = this.getAllServices();

    services.subscribe(data => {
      data.forEach(service => {
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

        currentNode.children.push(new TreeNode(service.name, [], service));
      });
    });

    return root;
  }
}
