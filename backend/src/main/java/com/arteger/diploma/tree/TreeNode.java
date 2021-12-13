package com.arteger.diploma.tree;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class TreeNode<E extends AbstractNode> {
    @Getter
    private E value;
    @Getter
    private Set<TreeNode<E>> children;

    public void addChild(TreeNode<E> child) {
        children.add(child);
    }

    public TreeNode(E data) {
        value = data;
        children = new HashSet<>();
    }
}
