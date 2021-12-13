import { DataNode } from "./datanode";

export interface DataTreeNode {
    value: DataNode,
    children: DataTreeNode[]
}