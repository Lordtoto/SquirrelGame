package Spiel.ui;

import Spiel.console.Command;
import Spiel.core.BoardView;

public interface UI {

    Command getCommand();

    void render(BoardView view);

    void message(String msg);

    void startConsoleInput();

}