package Spiel.console.myFavCmd;

import Spiel.console.ScanException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyFavoriteCommandProcessor {

    private final PrintStream outputStream = System.out;
    private final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public void process() {
        MyFavoriteCommandScanner commandScanner = new MyFavoriteCommandScanner(MyFavoriteCommandType.values(), inputReader);

        while (true) {
            try {

                MyFavoriteCommand myFavoriteCommand;
                myFavoriteCommand = commandScanner.next();

                Object[] params = myFavoriteCommand.getParams();

                MyFavoriteCommandTypeInfo commandType =  myFavoriteCommand.getCommandType();
                String methodName = commandType.getMethodName().toLowerCase();
                Class<?> methodClass = MyFavoriteCommandExecution.class;
                MyFavoriteCommandExecution execution = new MyFavoriteCommandExecution();
                Method method;

                if (params.length > 0) {
                    method = methodClass.getMethod(methodName, params[0].getClass(), params[1].getClass());
                    method.invoke(execution, params[0], params[1]);
                }
                else {
                    method = methodClass.getMethod(methodName);
                    method.invoke(execution);
                }

        } catch (ScanException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MyFavoriteCommandProcessor processor = new MyFavoriteCommandProcessor();
        processor.process();
    }
}
