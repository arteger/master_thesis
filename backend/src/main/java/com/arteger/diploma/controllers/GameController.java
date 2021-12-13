package com.arteger.diploma.controllers;

import com.arteger.diploma.data.DataNode;
import com.arteger.diploma.data.Solution;
import com.arteger.diploma.dto.StringResponse;
import com.arteger.diploma.games.market.MarketGame;
import com.arteger.diploma.services.GameService;
import com.arteger.diploma.tree.Tree;
import com.arteger.diploma.tree.TreeNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class GameController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private GameService gameService;

    @PostMapping("/start")
    @CrossOrigin
    public ResponseEntity<TreeNode<DataNode>> start() {
//        new Thread(() -> gameService.computeGame(new MarketGame(), 20, (dataNode ->
//                this.simpMessagingTemplate.convertAndSend("/topic/data", dataNode.getRoot())))).run();
        Tree<DataNode> dataNodeTree = gameService.computeGame(new MarketGame(), 20, (dataNode) -> {
        });

        System.out.println("Finished calculating");
        return ResponseEntity.ok().body(dataNodeTree.getAsTree());
    }

    @PostMapping("/start1")
    public ResponseEntity<Map<Object, DataNode>> start1() {
        Tree<DataNode> dataNodeTree = gameService.computeGame(new MarketGame(), 20, (dataNode) -> {
        });

        System.out.println("Finished calculating");
        Map<Object, DataNode> asFlatMap = dataNodeTree.getAsFlatMap();
        return ResponseEntity.ok(asFlatMap);
    }

    @PostMapping("/startAsync")
    @CrossOrigin
    public ResponseEntity<StringResponse> startAsync() {
        String id = gameService.computeGameAsync(new MarketGame(), 10, (dataNode) -> {
        });

        return ResponseEntity.ok(new StringResponse(id));
    }

    @PostMapping("/getResult/{id}")
    @CrossOrigin
    public ResponseEntity<Map<Object, DataNode>> getResult(@PathVariable("id") String id) {
        Tree<DataNode> dataNodeTree = gameService.getTreeById(id);
        Map<Object, DataNode> asFlatMap = dataNodeTree.getAsFlatMap();
        return ResponseEntity.ok(asFlatMap);
    }

    @GetMapping("/getSolution/{id}")
    @CrossOrigin
    public ResponseEntity<Solution> getSolution(@PathVariable("id") String id) {
        Tree<DataNode> dataNodeTree = gameService.getTreeById(id);
        Solution solution = gameService.findSolution(dataNodeTree.getRoot());
        return ResponseEntity.ok(solution);
    }

}
