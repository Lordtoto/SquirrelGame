package Spiel.gamemodes;

import Spiel.State;
import Spiel.console.Command;
import Spiel.console.GameCommandProcessor;
import Spiel.console.GameCommandType;
import Spiel.ui.UI;

import java.util.*;

public class BotMode extends Game {

    private int move = 0;
    private final int moves;
    private final int timeout;

    public BotMode(State state, UI ui) {
        super(state, ui);
        this.moves = state.getBoard().getBoardConfig().getMoves();
        this.timeout = state.getBoard().getBoardConfig().getTimeout();
        createBots();
        state.initializeScores();
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ui.message(state.getBoard().getMasterSquirrelString());
                render();
                processInput();
                update();
                move++;
                if (move == moves) {
                    move = -timeout;
                    state.getBoard().setFreezeTimer(timeout);
                    state.updateScores();
                    resetMasters();
                    System.out.println(state.getScores());
                    state.printHighScore();
                }
            }
        }, 0, 1000 / FPS);
    }

    @Override
    protected void update() {
        state.update();
    }

    @Override
    protected void render() {
        ui.render(state.flattenedBoard());
    }

    @Override
    protected void processInput() {
        GameCommandProcessor processor = new GameCommandProcessor(state.getBoard());
        Command cmd = ui.getCommand();
        if (cmd == null || cmd.getCommandType() != GameCommandType.EXIT) return;
        processor.process(cmd);
    }


}
