package com.josefigueredo.playground.aloha4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CLIExecutor {

    private final List<Command> cliOperations = new ArrayList<>();
    private final CommandReceiver commandReceiver = new CommandReceiver();

    public String executeCommand(Command command) {
        return executeCommand(command, true);
    }

    private String executeCommand(Command command, boolean saveCommand) {
        String response = "";
        switch(command.getCommand()) {
            case "pwd": {
                response = commandReceiver.pwd();
                break;
            }
            case "ls": {
                response = commandReceiver.ls(command);
                break;
            }
            case "mkdir": {
                response = commandReceiver.mkdir(command);
                break;
            }
            case "cd": {
                response = commandReceiver.cd(command);
                break;
            }
            case "touch": {
                response = commandReceiver.touch(command);
                break;
            }
            default:
                break;
        }

        if (saveCommand) {
            cliOperations.add(command);
        }

        return response;
    }

    public void executeAllCommands() {
        cliOperations.forEach(command -> System.out.println(executeCommand(command, false)));
    }

    public void serializeOperations(String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(cliOperations);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void deserializeOperations(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            cliOperations.clear();
            cliOperations.addAll ((ArrayList<Command>) ois.readObject());
        }
        catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
    }
}