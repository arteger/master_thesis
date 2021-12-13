package com.arteger.diploma.tree;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Tree<E extends AbstractNode> {
    @Getter
    private TreeNode<E> root;
    private HashMap<Object, TreeNode<E>> flatTreeMap;

    public Tree() {
        flatTreeMap = new HashMap<>();
    }

    public void addNode(E element) {
        TreeNode<E> node = new TreeNode<>(element);
        if (root == null) {
            root = node;
        }
        flatTreeMap.put(element.getId(), node);
        if (flatTreeMap.containsKey(element.getParentId())) {
            flatTreeMap.get(element.getParentId()).addChild(node);
        }

    }

    public TreeNode<E> getAsTree() {
        return root;
    }

    public Map<Object,E> getAsFlatMap() {
        Map<Object, E> flatMap = new HashMap<>();
        addElementToMapRecursively(root, flatMap);
        return flatMap;
    }

    private void addElementToMapRecursively(TreeNode<E> element, Map<Object, E> map) {
        map.put(element.getValue().getId(), element.getValue());
        for (TreeNode<E> child : element.getChildren()) {
            addElementToMapRecursively(child, map);
        }
    }


}
