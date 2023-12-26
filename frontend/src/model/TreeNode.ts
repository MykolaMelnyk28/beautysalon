import {ServiceModel} from "./ServiceModel";

export class TreeNode {
  name: string;
  value: ServiceModel | undefined;
  children: TreeNode[];

  constructor(name: string, children: TreeNode[] = [], value: ServiceModel | undefined = undefined) {
    this.name = name;
    this.children = children;
    this.value = value;
  }

}
