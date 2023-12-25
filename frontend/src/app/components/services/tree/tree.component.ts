import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TreeNode} from "../../../../model/TreeNode";

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent {
  @Input() root: TreeNode | null = null;
  private collapsedNodes: Set<TreeNode> = new Set<TreeNode>();
  @Output() nodeClicked: EventEmitter<TreeNode> = new EventEmitter<TreeNode>();
  @Output() nodeNotLeafClicked: EventEmitter<TreeNode> = new EventEmitter<TreeNode>();
  @Output() nodeLeafClicked: EventEmitter<TreeNode> = new EventEmitter<TreeNode>();


  handleClick(node: TreeNode) {
    if (node && node.children.length === 0) {
      this.nodeLeafClicked.emit(node);
    } else {
      this.toggleCollapse(node);
      this.nodeNotLeafClicked.emit(node);
    }
    this.nodeClicked.emit(node);
  }

  private toggleCollapse(node: TreeNode) {
    if (this.collapsedNodes.has(node)) {
      this.collapsedNodes.delete(node);
    } else {
      this.collapsedNodes.add(node);
      node?.children.forEach(x => {
        this.toggleCollapse(x);
      })
    }
  }

  isNodeCollapsed(node: TreeNode): boolean {
    return this.collapsedNodes.has(node);
  }
}
