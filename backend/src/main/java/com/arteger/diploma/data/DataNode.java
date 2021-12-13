package com.arteger.diploma.data;

import com.arteger.diploma.tree.AbstractNode;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class DataNode implements AbstractNode {
    String id;
    String parentId;
    String player;
    String turn;
    int round;
    boolean gameIsFinished;
    Set<Income> incomes;
}
