import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TreeNode} from "../../../../model/TreeNode";

@Component({
  selector: 'app-tree-node',
  templateUrl: './tree-node.component.html',
  styleUrls: ['./tree-node.component.css']
})
export class TreeNodeComponent {
  @Input() node: TreeNode = new TreeNode("");
  @Input() isCollapsed: boolean = false;

  isLeaf() {
    return this.node?.children.length === 0;
  }

}
