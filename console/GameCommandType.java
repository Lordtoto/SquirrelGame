package Spiel.console;

public enum GameCommandType implements CommandTypeInfo {

    HELP("help", "help", " * list all commands"),
    EXIT("exit", "exit", " * exit program "),
    ALL("all", "all", " * "),
    LEFT("left", "left", " * move master to the left "),
    UP("up", "up", " * move master up "),
    DOWN("down", "down", " * move master down"),
    RIGHT("right", "right", " * move master to the right"),
    MASTER_ENERGY("masterEnergy", "masterEnergy", " * show energy of the master"),
    SPAWN_MINI("spawnMini", "spawnMini", " <param> * let mini spawn with param life ", int.class),
    ;

    private final String methodName;
    private final String name;
    private final String helpText;
    private final Class<?> [] paramTypes;

    GameCommandType(String methodName, String name, String helpText, Class<?>... paramTypes) {
        this.methodName = methodName;
        this.name = name;
        this.helpText = helpText;
        this.paramTypes = paramTypes;
    }


    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHelpText() {
        return helpText;
    }

    @Override
    public Class<?>[] getParamTypes() {
        return paramTypes;
    }
}
