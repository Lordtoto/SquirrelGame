package Spiel;

import Spiel.core.Board;
import Spiel.core.BoardConfig;
import Spiel.gamemodes.BotMode;
import Spiel.gamemodes.Game;
import Spiel.gamemodes.PlayerMode;
import Spiel.ui.ConsoleUI;
import Spiel.ui.FxUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    private static void startGame(Game game) throws InterruptedException {
        Thread.sleep(1000);
        game.run();
    }

    public static void main(String[] args) {

        Board board = new Board(new BoardConfig());
        ConsoleUI ui = new ConsoleUI();
        State state = new State(board);
        final Game game = new PlayerMode(state, ui);
        //startGame(game);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Board board = new Board(new BoardConfig());
        ConsoleUI ui = new ConsoleUI();
        State state = new State(board);
        FxUI fxUI = FxUI.createInstance(board.getBoardConfig().getSize());
        final Game game = new BotMode(state, fxUI);
        //final Game game = new PlayerMode(state, fxUI);
        primaryStage.setScene(fxUI);
        primaryStage.setHeight(500);
        primaryStage.setWidth(1000);
        primaryStage.setTitle("Diligent Squirrel");
        fxUI.getWindow().setOnCloseRequest(evt -> System.exit(-1));
        primaryStage.show();

        startGame(game);
    }
}
