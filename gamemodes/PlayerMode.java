package Spiel.gamemodes;

import Spiel.State;
import Spiel.XY;
import Spiel.console.Command;
import Spiel.console.GameCommandProcessor;
import Spiel.entities.HandOperatedMasterSquirrel;
import Spiel.ui.UI;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerMode extends Game {

    private final boolean bots;

    public PlayerMode(State state, UI ui, boolean bots) {
        super(state, ui);
        this.bots = bots;
        createSquirrel();
    }

    public PlayerMode(State state, UI ui) {
        super(state, ui);
        this.bots = false;
        createSquirrel();
    }

    @Override
    public void run() {
        ui.startConsoleInput();
        Timer timer = new Timer();
        Timer renderTimer = new Timer();


        renderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                render();
            }
        },0, 1000/30);


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ui.message(state.getBoard().getMasterSquirrelString());
                processInput();
                update();
            }
        },0, 1000/FPS);
    }

    @Override
    protected void processInput() {
        GameCommandProcessor processor = new GameCommandProcessor(state.getBoard());
        if (state.getBoard().getHandOperatedSquirrel().getTimeout() == 0) {
            Command cmd = ui.getCommand();
            if (cmd == null) {
                state.getBoard().getHandOperatedSquirrel().setNextMove(new XY(0,0));
                return;
            }
            processor.process(cmd);
        }
    }

    @Override
    protected void render() {
        ui.render(state.flattenedBoard());
    }

    @Override
    protected void update() {
        state.update();
    }

    private void createSquirrel() {
        HandOperatedMasterSquirrel handOpMaster = new HandOperatedMasterSquirrel(state.getBoard().getRandomUnusedLoc(), 100);
        state.getBoard().getEntitySet().add(handOpMaster);
        if (bots) createBots();
    }
}
