import { Injectable, Input } from '@angular/core';
import Graph from "graphology";
import circularLayout from "graphology-layout/circular";
import { DataNode } from 'src/app/data/datanode';
import { DataTreeNode } from 'src/app/data/datatreenode';
import * as internal from 'stream';
@Injectable({
  providedIn: 'root'
})
export class GraphService {

  graph: Graph;
  x = 0;
  nodeRadius = 10
  elementMaxWidth;

  constructor() {
    this.graph = new Graph();
    this.elementMaxWidth = 5
  }

  public updateGraph(message: DataTreeNode, count) {
    this.graph.clear()
    let maxWidth = this.leafsCount(message)
    console.log(maxWidth)
    maxWidth = maxWidth * this.elementMaxWidth
    let yDiff = 5
    this.drawNode(message, maxWidth, maxWidth / 2.0, 1, 0, 1, yDiff)
  }

  public updateGraphWithMap(flatMap: Map<String, DataNode>) {
    let message = buildTreeFromFlatMap(flatMap)
    let count = flatMap.size
    this.updateGraph(message, count)
  }

  drawNode(message: DataTreeNode, maxWidth: number, parentX, parentsCount, childIndex, childrenCount, yDiff) {
    let elementDivWidth = maxWidth / parentsCount
    let start = parentX - elementDivWidth / 2 + (elementDivWidth / childrenCount * 0.5)
    let x = start + childIndex * (elementDivWidth / childrenCount)
    let y = -1 * message.value.round * yDiff
    let label
    let edgeLabel
    if (message.value.player != null) {
      label = message.value.player + ":" + message.value.turn
    } else {
      label = message.value.turn
    }
    if (message.value.gameIsFinished) {
      label += "\r\n Outcomes:\r\n";
      message.value.incomes.forEach(income => {
        label += income.player + ":" + income.value + "\rn";
      });
    }
    this.graph.addNode(message.value.id, { size: this.nodeRadius, x: x, y: y, label: label })
    let newIndex = 0
    for (const child of message.children) {
      this.drawNode(child, maxWidth / parentsCount, x, childrenCount, newIndex, message.children.length, yDiff)
      this.graph.addEdge(message.value.id, child.value.id, { label: edgeLabel, type: "arrow", size: 1 })
      newIndex += 1
    }
  }

  leafsCount(message: DataTreeNode) {
    if (message.children.length == 0) {
      return 1
    } else {
      let count = 0
      for (const child of message.children) {
        count += this.leafsCount(child)
      }
      return count
    }
  }

  defineMaxWidth(flatMap: Map<string, DataNode>): number {
    let widthMap = new Map<number, number>()
    for (const key of flatMap.keys()) {
      let value = flatMap.get(key)
      if (widthMap.get(value.round) == undefined) {
        widthMap.set(value.round, 1)
      } else {
        widthMap.set(value.round, widthMap.get(value.round) + 1)
      }
    }
    let max = 0
    widthMap.forEach((value, key) => {
      if (max < value) {
        max = value
      }
    })
    return max
  }

  buildFlatMap(message: DataTreeNode): Map<string, DataNode> {
    let map = new Map()
    this.addElementToMap(message, map)
    return map
  }

  addElementToMap(element, map) {
    map.set(element.value.id, element.value)
    for (const child of element.children) {
      this.addElementToMap(child, map)
    }
  }

  public getGraph(): Graph {
    return this.graph
  }


  public notifyUpdates() {

  }

}


function buildTreeFromFlatMap(flatMap: Map<String, DataNode>): DataTreeNode {
  let treeNodes = new Map<string, DataTreeNode>()
  let root = null

  for (const key of flatMap.keys()) {
    let element = flatMap.get(key)
    treeNodes.set(element.id, {
      value: element,
      children: []
    })
    if (element.round == -1) {
      root = treeNodes.get(element.id)
    }
  }
  for (const key of treeNodes.keys()) {
    let element = treeNodes.get(key)
    if (element.value.parentId != null) {
      treeNodes.get(element.value.parentId).children.push(element)
    }
  }
  return root
}

