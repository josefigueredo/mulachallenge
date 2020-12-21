package com.josefigueredo.playground.aloha4;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T data;
    private List<Node<T>> children;
    private Node<T> parent;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;

        if (obj instanceof Node) {
            return ((Node<?>) obj).getData().equals(this.data);
        }

        return false;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

}