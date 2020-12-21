package com.josefigueredo.playground.aloha4;

public class NodeData implements Comparable {
    private FileSystemType type;
    private String name;

    public NodeData(FileSystemType type, String name) {
        this.setType(type);
        this.setName(name);
    }

    @Override
    public int compareTo(Object o) {
        NodeData nodeData = (NodeData) o;
        return Integer.compare(getType().compareTo(nodeData.getType()), 0);
    }

    public FileSystemType getType() {
        return type;
    }

    public void setType(FileSystemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
