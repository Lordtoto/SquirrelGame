package Spiel.ui;

import Spiel.console.Command;
import Spiel.console.CommandScanner;
import Spiel.console.GameCommandType;
import Spiel.console.ScanException;
import Spiel.core.BoardView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


public class ConsoleUI implements UI {
    private Command buffer;

    public void startConsoleInput() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setNewBufferCommand();
            }
        },0,500);
    }

    public Command getCommand() {
        Command output = buffer;
        if (output != null) buffer = null;
        return output;
    }

    private void setNewBufferCommand() {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (buffer == null) {
                buffer = new CommandScanner(GameCommandType.values(), inputReader).next();
            }
        } catch (ScanException e) {
            System.out.println(e.getMessage());
        }
    }

    public void render(BoardView view) {
        StringBuilder s = new StringBuilder();
        for (int j = view.getSize().getY()-1; j >= 0; j--) {
            for (int i = 0; i < view.getSize().getX(); i++) {
                if (view.getEntityType(i, j) == null) {
                    s.append("      " + "    ");
                } else {
                    s.append((view.getEntityType(i, j).toString() + "  "), 0, 6).append("    ");
                }
            }
            s.append("\n");
        }
        System.out.println(s);
    }

    @Override
    public void message(String msg) {
        System.out.println(msg);
    }
}