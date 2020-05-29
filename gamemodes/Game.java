package Spiel.gamemodes;

import Spiel.State;
import Spiel.core.BoardConfig;
import Spiel.core.MasterSquirrelBot;
import Spiel.entities.MasterSquirrel;
import Spiel.ui.UI;

public abstract class Game {
    private final BoardConfig boardConfig;
    protected final State state;
    protected final UI ui;
    protected final int FPS = 4;

    public Game(State state,UI ui) {
        this.ui = ui;
        this.state = state;
        this.boardConfig = state.getBoard().getBoardConfig();
    }

    public abstract void run();

    public void runAlt() {
        while (true) {
            render();
            processInput();
            update();
        }
    }

    protected abstract void processInput();

    protected abstract void render();

    protected abstract void update();

    protected void createBots() {
        for (String botName : boardConfig.getBotNames()) {
            MasterSquirrelBot master = new MasterSquirrelBot(state.getBoard().getRandomUnusedLoc(), botName, boardConfig.getMoves());
            state.getBoard().getEntitySet().add(master);
        }
    }

    protected void resetMasters() {
        state.getBoard().deleteMinis();
        for (MasterSquirrel master : state.getBoard().getMasterSquirrel()) {
            master.reset(state.getBoard().getRandomUnusedLoc());
        }
    }
}