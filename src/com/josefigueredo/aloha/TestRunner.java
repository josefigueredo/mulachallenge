package com.josefigueredo.playground.aloha4;


import java.util.Scanner;

public class TestRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String commandIn = "";

        CLIExecutor cliExecutor = new CLIExecutor();

        while(!commandIn.equals("quit")) {
            commandIn =  scanner.nextLine();

            Command command = new Command(commandIn.split(" "));
            System.out.println(cliExecutor.executeCommand(command));
        }

        // Test serialization/deserialization:
        cliExecutor.serializeOperations("aloha4.data");
        cliExecutor.deserializeOperations("aloha4.data");
        cliExecutor.executeAllCommands();
    }
}
