package Spiel.console.myFavCmd;

import java.lang.reflect.Method;
import java.util.Arrays;

public enum MyFavoriteCommandType implements MyFavoriteCommandTypeInfo {
    HELP("help" ,"help"," * list all commands "),
    EXIT("exit" ,"exit", " * exit program "),
    ADDI("addi" ,"addi", " <param1> <param2> * simple integer add ", int.class, int.class),
    ADDF("addf" ,"addf", " <param1> <param2> * simple float add ", float.class, float.class),
    ECHO( "echo" ,"echo", " <param1> <param2> * echos param1 string param2 times ", String.class, int.class)
    ;

    private final String methodName;
    private final String name;
    private final String helpText;
    private final Class<?> [] paramTypes;
    private Method method;
    private Object target;

    MyFavoriteCommandType(String methodName, String name, String helpText, Class<?>... paramTypes) {
        this.methodName = methodName;
        this.name = name;
        this.helpText = helpText;
        this.paramTypes = paramTypes;
    }


    public String getName() {
        return name;
    }


    public String getHelpText() {
        return helpText;
    }


    public Class<?>[] getParamTypes() {
        return paramTypes;
    }


    public String getMethodName() {
        return methodName;
    }

    public String printCommands() {
        StringBuilder s = new StringBuilder();
        for (MyFavoriteCommandType m : MyFavoriteCommandType.values()) {
            s.append(m).append(" ");
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "MyFavoriteCommandType{" +
                "name='" + name + '\'' +
                ", helpText='" + helpText + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                '}';
    }
}
