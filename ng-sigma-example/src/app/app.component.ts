import { Component, OnInit } from '@angular/core';
import Graph from 'graphology';
import { WebsocketService } from './services/websocket/websocket.service';

import { GraphService } from './services/graph/graph.service';
import { DataNode } from './data/datanode';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RequestService } from './services/requestService/requests.service';
import { StringResponse } from './data/stringresponse';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {
  title = 'angular8-springboot-websocket';
  graph: Graph
  lastNodesCount = -1
  interval

  constructor(
    private webSocketAPI: WebsocketService,
    private graphService: GraphService,
    private requestsService: RequestService
  ) {
    // webSocketAPI.init(this)
    this.graph = graphService.getGraph()
  }

  ngOnInit() {
    this.connect()
  }

  connect() {
    this.webSocketAPI._connect();
    console.log("Socket connected")
  }

  disconnect() {
    this.webSocketAPI._disconnect();
    console.log("Socket disconnected")
    this.webSocketAPI._connect();
  }

  handleMessage(message) {
    console.log("Message")
    this.graphService.updateGraph(message, 10)
    this.graph = this.graphService.getGraph()
  }

  start() {
    this.requestsService.startService().subscribe((stringResponse: StringResponse) => {
      this.interval = setInterval(() => {
        this.requestsService.getTree(stringResponse.data)
          .subscribe((flatTree: Map<String, DataNode>) => {
            let map = new Map(Object.entries(flatTree))
            if (map.size != this.lastNodesCount) {
              this.lastNodesCount = map.size
              this.graphService.updateGraphWithMap(map)
            } else {
              clearInterval(this.interval)
            }
          })
      }, 3000)
    })
  }

}