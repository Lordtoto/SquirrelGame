package Spiel.console;

import Spiel.core.Board;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameCommandProcessor {

    private final PrintStream outputStream = System.out;
    private final BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
    private final Board board;
    private static final Logger LOGGER = Logger.getLogger(GameCommandProcessor.class.getName());

    public GameCommandProcessor(Board board) {
        this.board = board;
    }

    public void process(Command command) {
            try {
                Object[] params = command.getParams();

                GameCommandType commandType = (GameCommandType) command.getCommandType();
                GameCommandExecution execution = new GameCommandExecution(board);
                String methodName = commandType.getMethodName();
                Class<GameCommandExecution> methodClass = GameCommandExecution.class;
                Method method;

                if (params.length > 0) {
                    if ((Integer)params[0] >= board.getHandOperatedSquirrel().getEnergy()) {
                        throw new NotEnoughEnergyException("Not enough energy");
                    }
                    if ((Integer)params[0] < 100) throw new NotEnoughEnergyException("MiniSquirrel should get at least 100 life");
                    method = methodClass.getMethod(methodName, params[0].getClass());
                    method.invoke(execution, params[0]);
                }

                else {
                    method = methodClass.getMethod(methodName);
                    method.invoke(execution);
                }
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NotEnoughEnergyException e) {
                LOGGER.log(Level.WARNING,"Exception: " + e.getMessage());
                board.freeze();
                System.out.println(e);
            }
    }
}
