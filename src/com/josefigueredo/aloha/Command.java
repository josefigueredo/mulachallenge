package com.josefigueredo.playground.aloha4;

import java.io.Serializable;

public class Command implements Serializable {

    private String command;
    private String[] arguments;

    public Command(String[] commandArgs) {
        this.command = commandArgs[0];
        if (commandArgs.length > 1) {
            int args = commandArgs.length - 1;
            this.arguments = new String[args];
            System.arraycopy(commandArgs,1, this.arguments, 0, args);
        }
    }

    public String getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments;
    }

}
