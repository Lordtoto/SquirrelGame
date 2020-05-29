package Spiel.console;

import java.util.Arrays;

public class Command {

    private final CommandTypeInfo commandType;
    private final Object[] params;

    public Command(CommandTypeInfo commandType, Object... params) {
        this.commandType = commandType;
        this.params = params;
    }

    public CommandTypeInfo getCommandType() {
        return commandType;
    }

    public Object[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandType=" + commandType +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
