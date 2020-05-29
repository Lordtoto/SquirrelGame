package Spiel.console;

import java.io.BufferedReader;
import java.io.IOException;

public class CommandScanner {
    private final CommandTypeInfo[] commandTypeInfos;
    private final BufferedReader inputReader;

    public CommandScanner(CommandTypeInfo[] commandTypes, BufferedReader inputReader) {
        this.commandTypeInfos = commandTypes;
        this.inputReader = inputReader;
    }

    public Command next() throws ScanException {
        System.out.print(">> ");
        String cmd = readLine();

        if (cmd == null) {
            return null;
        }

        String[] command = cmd.split(" ");

        for (CommandTypeInfo cmdInfo : commandTypeInfos) {
            if (cmdInfo.getName().equals(command[0])) {
                if (command.length > 2) {
                    throw new ScanException("");
                }
                if (command.length == 2) {
                    return new Command(cmdInfo, Integer.parseInt(command[1]));
                }
                return new Command(cmdInfo);
            }
        }
        throw new ScanException("Command not found!");
    }

    private String readLine() {
        try {
            return inputReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
