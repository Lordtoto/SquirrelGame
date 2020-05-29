package Spiel.console;

import Spiel.XY;
import Spiel.core.Board;
import Spiel.entities.HandOperatedMasterSquirrel;

public class GameCommandExecution {
    private final Board board;
    private final HandOperatedMasterSquirrel squirrel;

    public GameCommandExecution(Board board) {
        this.board = board;
        squirrel = board.getHandOperatedSquirrel();
    }

    public void right() {
        squirrel.setNextMove(XY.RIGHT);
    }

    public void left() {
        squirrel.setNextMove(XY.LEFT);
    }

    public void up() {
        squirrel.setNextMove(XY.UP);
    }

    public void down() {
        squirrel.setNextMove(XY.DOWN);
    }

    public void all() {
        board.freeze();
        System.out.println(board.getEntitySet().toString());
        System.out.println();
    }

    public void help() {
        board.freeze();
        for (GameCommandType t : GameCommandType.values()) {
            System.out.print(t.getName() + t.getHelpText() + " |\n");
        }
        System.out.println();
    }

    public void masterEnergy() {
        board.freeze();
        System.out.println("MasterEnergy: " + squirrel.getEnergy());
    }

    public void spawnMini(Integer energy) {
        squirrel.createMiniSquirrel(energy);
    }

    public void exit() {
        System.exit(0);
    }
}
