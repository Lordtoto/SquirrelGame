package Spiel.ui;

import Spiel.XY;
import Spiel.console.Command;
import Spiel.console.GameCommandType;
import Spiel.core.BoardView;
import Spiel.entities.EntityType;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FxUI extends Scene implements UI {
    final Canvas boardCanvas;
    final Label msgLabel;
    static Command buffer = null;

    public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
        super(parent);
        this.boardCanvas = boardCanvas;
        this.msgLabel = msgLabel;
    }

    public static FxUI createInstance(XY boardSize) {
        Label statusLabel = new Label();
        Canvas boardCanvas = new Canvas(boardSize.getX() * 20, boardSize.getY() * 20);
        VBox top = new VBox();
        top.getChildren().add(boardCanvas);
        top.getChildren().add(statusLabel);
        statusLabel.setText("Hallo Welt");
        final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel);
        fxUI.setOnKeyPressed(event ->
                inputBuffer(event.getCode())
        );
        return fxUI;
    }

    public static void inputBuffer(KeyCode keycode) {
        switch (keycode) {
            case W: buffer = new Command(GameCommandType.UP); break;
            case A: buffer = new Command(GameCommandType.LEFT); break;
            case D: buffer = new Command(GameCommandType.RIGHT); break;
            case S: buffer = new Command(GameCommandType.DOWN); break;
            case F: buffer = new Command(GameCommandType.SPAWN_MINI, 100); break;
            case ESCAPE: buffer = new Command(GameCommandType.EXIT);
        }
    }


    @Override
    public Command getCommand() {
        Command output = buffer;
        if (output != null) buffer = null;
        return output;
    }

    @Override
    public void render(final BoardView view) {
        Platform.runLater(() -> repaintBoardCanvas(view));
    }

    private void repaintBoardCanvas(BoardView view) {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        XY viewSize = view.getSize();

        for (int j = viewSize.getY() - 1; j >= 0; j--) {
            for (int i = 0; i < viewSize.getX(); i++) {
                EntityType entityTypeTarget = view.getEntityType(i, viewSize.getY() - j - 1);
                if (entityTypeTarget != EntityType.NONE) {
                    switch (entityTypeTarget) {
                        case MasterSquirrel:
                            gc.setFill(Color.BLUE);
                            break;
                        case Wall:
                            gc.setFill(Color.ORANGE);
                            break;
                        case GoodBeast:
                        case GoodPlant:
                            gc.setFill(Color.GREEN);
                            break;
                        case BadBeast:
                        case BadPlant:
                            gc.setFill(Color.RED);
                            break;
                        case MiniSquirrel:
                            gc.setFill(Color.BLUE);
                            break;
                    }
                    if (entityTypeTarget == EntityType.Wall) {
                        gc.fillRect(20 * i, 20 * j, 20, 20);
                        continue;
                    }
                    if (!entityTypeTarget.isCharacter())
                        gc.fillRect(20 * i, 20 * j, 19, 19);
                    else if (entityTypeTarget == EntityType.MasterSquirrel) {
                        gc.fillRect(20 * i, 20 * j, 19, 19);
                    }
                        gc.fillOval(20 * i, 20 * j, 19, 19);
                }
            }
        }
    }


    @Override
    public void message(final String msg) {
        Platform.runLater(() -> msgLabel.setText(msg));
    }

    @Override
    public void startConsoleInput() {

    }

}