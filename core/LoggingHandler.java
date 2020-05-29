package Spiel.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingHandler implements InvocationHandler {
    private final Object target;
    private static final Logger LOGGER = Logger.getLogger(LoggingHandler.class.getName());
    private final ConsoleHandler handler = new ConsoleHandler();
    private final SimpleFormatter formatter = new SimpleFormatter();

    {
        LOGGER.addHandler(handler);
        handler.setFormatter(formatter);
        LOGGER.setLevel(Level.OFF);
    }

    public LoggingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        StringBuilder msg = new StringBuilder();
        String name = target.getClass().getSimpleName();
        String methodName = method.getName();
        msg.append(name).append(".").append(methodName).append("(");

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (i == args.length - 1) {
                    msg.append(args[i].getClass().getSimpleName()).append(" ").append(args[i].toString());
                    break;
                }
                msg.append(args[i].getClass().getSimpleName()).append(" ").append(args[i].toString()).append(", ");
            }
            msg.append(") invoked.\n");
        }

        if (methodName.equals("spawnMiniBot") || methodName.equals("move")) {
            LOGGER.info(msg.toString());
        } else {
            LOGGER.log(Level.FINE, msg.toString());
        }
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "Logging failed! Exception: " + e.getMessage());
        }
        return null;
    }
}
