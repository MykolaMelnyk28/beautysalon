export class TreeNode {
  name: string;
  children: TreeNode[];

  constructor(name: string, children: TreeNode[] = []) {
    this.name = name;
    this.children = children;
  }
}
