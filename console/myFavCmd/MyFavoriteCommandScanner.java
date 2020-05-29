package Spiel.console.myFavCmd;

import Spiel.console.ScanException;

import java.io.BufferedReader;
import java.io.IOException;

public class MyFavoriteCommandScanner {

        private final MyFavoriteCommandTypeInfo[] myFavoriteCommandTypeInfos;
        private final BufferedReader inputReader;

        public MyFavoriteCommandScanner(MyFavoriteCommandTypeInfo[] myFavoriteCommandTypeInfos, BufferedReader inputReader) {
            this.myFavoriteCommandTypeInfos = myFavoriteCommandTypeInfos;
            this.inputReader = inputReader;
        }

        public MyFavoriteCommand next() throws ScanException {
            System.out.print(">> ");
            String cmd = readLine();

            if (cmd == null) {
                return null;
            }

            String[] commandAndParams = cmd.split(" ");

            for (MyFavoriteCommandTypeInfo MyFavoriteCommandType : myFavoriteCommandTypeInfos) {
                if (MyFavoriteCommandType.getName().equals(commandAndParams[0])) {
                    if (commandAndParams.length > 3) {
                        throw new ScanException("command too long");
                    }
                    if (commandAndParams.length == 1) {
                        return new MyFavoriteCommand(MyFavoriteCommandType);
                    }
                    if (MyFavoriteCommandType.getParamTypes()[0] == int.class) {
                        if (commandAndParams[1].contains(".") || commandAndParams[2].contains(".")) {
                            throw new ScanException("addi only for adding integer!");
                        }
                        return new MyFavoriteCommand(MyFavoriteCommandType, Integer.parseInt(commandAndParams[1]), Integer.parseInt(commandAndParams[2]));
                    }
                    if (MyFavoriteCommandType.getParamTypes()[0] == float.class) {
                        return new MyFavoriteCommand(MyFavoriteCommandType, Float.parseFloat(commandAndParams[1]), Float.parseFloat(commandAndParams[2]));
                    }
                    if (MyFavoriteCommandType.getParamTypes()[0] == String.class) {
                        if (commandAndParams.length == 3) {
                            return new MyFavoriteCommand(MyFavoriteCommandType, commandAndParams[1], Integer.parseInt(commandAndParams[2]));
                        }
                    }
                }
            }
            throw new ScanException("Command not found!");
        }

        private String readLine() {
            try {
                return inputReader.readLine().toLowerCase();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
}
