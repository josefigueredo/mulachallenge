package com.josefigueredo.playground.aloha4;

import java.util.List;

public class CommandReceiver {

    Node<NodeData> rootDirectory;
    Node<NodeData> currentDirectory;

    public CommandReceiver() {
        rootDirectory = new Node<>(new NodeData(FileSystemType.DIRECTORY, ""));
        currentDirectory = rootDirectory;
    }

    public String quit() {
        System.out.println("Command: quit");
        return "";
    }

    private String getNameRecursive(Node<NodeData> node) {
        String response = "";
        if(node.getData().getType().equals(FileSystemType.DIRECTORY) && !node.getData().getName().equals("")) {
            response = getNameRecursive(node.getParent()) + "/";
        }
        return response + node.getData().getName();
    }

    public String pwd() {
        if (rootDirectory != currentDirectory) {
            return getNameRecursive(currentDirectory);
        }
        return "/";
    }

    private String listElements(List<Node<NodeData>> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(element -> stringBuilder.append(element.getData().getName()).append("\n"));
        return stringBuilder.toString();
    }

    private String listElementsRecursive(List<Node<NodeData>> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(element -> {
            stringBuilder.append(element.getData().getName()).append("\n");
            if(element.getChildren() != null) {
                stringBuilder.append(listElementsRecursive(element.getChildren())).append("\n");
            }
        });
        return stringBuilder.toString();
    }

    private Node<NodeData> existNodeData(List<Node<NodeData>> list, NodeData nodeData) {
        for(Node<NodeData> node: list) {
            if(node.getData().getType().equals(nodeData.getType()) && node.getData().getName().equals(nodeData.getName())) {
                return node;
            }
        }
        return null;
    }

    public String ls(Command command) {
        StringBuilder stringBuilder = new StringBuilder();

        if(command.getArguments() == null) {
            stringBuilder.append(listElements(currentDirectory.getChildren()));
        }
        else if((command.getArguments()[0]).equals("-r")) {
            stringBuilder.append(listElementsRecursive(currentDirectory.getChildren()));
        }
        else if((command.getArguments()[0]).contains("/")) {
            String[] dirs = (command.getArguments()[0]).split("/");
            Node<NodeData> base = rootDirectory;
            for(String dir: dirs) {
                if(!dir.equals("")) {
                    Node<NodeData> inner = existNodeData(base.getChildren(), new NodeData(FileSystemType.DIRECTORY, dir));
                    if (inner != null) {
                        base = inner;
                    } else {
                        return "Invalid path";
                    }
                }
            }
            stringBuilder.append(listElements(currentDirectory.getChildren()));
        }

        return stringBuilder.toString();
    }

    public String mkdir(Command command) {
        String directoryName = command.getArguments()[0];
        String response = "";
        if(directoryName.length() > 100) {
            response = "File name is over 100 characters";
        }
        Node<NodeData> existingNode = existNodeData(currentDirectory.getChildren(), new NodeData(FileSystemType.FILE, directoryName));
        if(existingNode == null) {
            Node<NodeData> node = new Node<>(new NodeData(FileSystemType.DIRECTORY, directoryName));
            currentDirectory.addChild(node);
        }
        else {
            response = "Directory name already exists";
        }

        return response;
    }

    public String cd(Command command) {
        String directoryName = command.getArguments()[0];
        String response = "";

        if (directoryName.equals("..")) {
            if (rootDirectory != currentDirectory) {
                currentDirectory = currentDirectory.getParent();
            }
        }
        else if((command.getArguments()[0]).contains("/")) {
            String[] dirs = (command.getArguments()[0]).split("/");
            Node<NodeData> base = rootDirectory;
            for(String dir: dirs) {
                if(!dir.equals("")) {
                    Node<NodeData> inner = existNodeData(base.getChildren(), new NodeData(FileSystemType.DIRECTORY, dir));
                    if (inner != null) {
                        base = inner;
                    } else {
                        return "Invalid path";
                    }
                }
            }
            currentDirectory = base;
        }
        else {
            Node<NodeData> existingNode = existNodeData(currentDirectory.getChildren(), new NodeData(FileSystemType.DIRECTORY, directoryName));
            if(existingNode != null) {
                currentDirectory = existingNode;
            }
            else {
                response = "Directory not found";
            }
        }

        return response;
    }

    public String touch(Command command) {
        String fileName = command.getArguments()[0];
        String response = "";
        if(fileName.length() > 100) {
            response = "File name is over 100 characters";
        }
        Node<NodeData> existingNode = existNodeData(currentDirectory.getChildren(), new NodeData(FileSystemType.FILE, fileName));
        if(existingNode == null) {
            Node<NodeData> node = new Node<>(new NodeData(FileSystemType.FILE, fileName));
            currentDirectory.addChild(node);
        }
        else {
            response = "File name already exists";
        }

        return response;
    }
}
