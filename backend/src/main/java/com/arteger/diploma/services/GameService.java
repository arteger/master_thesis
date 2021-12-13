package com.arteger.diploma.services;

import com.arteger.diploma.data.*;
import com.arteger.diploma.tree.Tree;
import com.arteger.diploma.tree.TreeNode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class GameService {

    private Map<String, Tree<DataNode>> gamesResults = new HashMap<>();
    private Map<String, Boolean> gamesFinished = new HashMap<>();

    public Tree<DataNode> computeGame(AbstractGame game, int turns, Consumer<Tree<DataNode>> dataNodeConsumer) {
        String nodeId = UUID.randomUUID().toString();
        Tree<DataNode> gameTree = runGame(game, turns, dataNodeConsumer, nodeId);
        return gameTree;
    }

    private Tree<DataNode> runGame(AbstractGame game, int turns, Consumer<Tree<DataNode>> dataNodeConsumer, String nodeId) {
        Tree<DataNode> gameTree = new Tree<>();
        gamesResults.put(nodeId, gameTree);
        DataNode initialGameState = DataNode.builder()
                .id(nodeId)
                .parentId(null)
                .turn("Initial game state")
                .player(null)
                .round(-1)
                .gameIsFinished(false)
                .build();
        gameTree.addNode(initialGameState);
        dataNodeConsumer.accept(gameTree);
        computeGameRecursive(game, dataNodeConsumer, turns, nodeId, gameTree);
        return gameTree;
    }

    public String computeGameAsync(AbstractGame game, int turns, Consumer<Tree<DataNode>> dataNodeConsumer) {
        String nodeId = UUID.randomUUID().toString();
        new Thread(() -> runGame(game, turns, dataNodeConsumer, nodeId)).start();

        return nodeId;
    }

    public Tree<DataNode> getTreeById(String id) {
        return gamesResults.get(id);
    }

    private void computeGameRecursive(AbstractGame game, Consumer<Tree<DataNode>> dataNodeConsumer, int turns, String parentId, Tree<DataNode> gameTree) {
        Player currentPlayer = game.getPlayers().get(game.getRound() % game.getPlayers().size());
        Set<Turn> possibleTurns = currentPlayer.getPossibleTurns(game);
        for (Turn possibleTurn : possibleTurns) {
            String nodeId = UUID.randomUUID().toString();
            Set<Income> incomes = null;
            AbstractGame copy = game.copy();
            possibleTurn.apply(copy);
            copy.nextRound();
            if (copy.gameIsFinished()) {
                incomes = copy.getPlayers().stream().map(player -> new Income(player.getTag(), player.getIncome(copy))).collect(Collectors.toSet());
            }
            DataNode dataNode = DataNode.builder()
                    .id(nodeId)
                    .parentId(parentId)
                    .player(currentPlayer.getTag())
                    .turn(possibleTurn.getName())
                    .round(game.getRound())
                    .gameIsFinished(copy.gameIsFinished())
                    .incomes(incomes)
                    .build();
            gameTree.addNode(dataNode);
            dataNodeConsumer.accept(gameTree);

            if (!copy.gameIsFinished() && copy.getRound() < turns) {
                computeGameRecursive(copy, dataNodeConsumer, turns, nodeId, gameTree);
            }
        }
    }

    private Solution findMaxMin(TreeNode<DataNode> node) {
        Solution bestSolution = null;
        for (TreeNode<DataNode> child : node.getChildren()) {
            Solution solution;
            if (child.getValue().isGameIsFinished()) {
                solution = new Solution();
                child.getValue().getIncomes().forEach(income -> solution.getOutcomes().put(income.getPlayer(), income.getValue()));
            } else {
                solution = findMaxMin(child);
            }
            try {
                if (bestSolution == null ||
                        solution.getOutcomes().get(child.getValue().getPlayer()) > bestSolution.getOutcomes().get(child.getValue().getPlayer())) {
                    bestSolution = solution;
                    bestSolution.getTurns().addFirst(new PlayerTurnPair(child.getValue().getPlayer(), child.getValue().getTurn()));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return bestSolution;
    }

    public Solution findSolution(TreeNode<DataNode> node) {
        Solution maxMin = findMaxMin(node);
        return maxMin;
    }

}
