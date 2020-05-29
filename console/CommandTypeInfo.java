package Spiel.console;

public interface CommandTypeInfo {

    String getMethodName();

    String getName();

    String getHelpText();

    Class<?>[] getParamTypes();
}
