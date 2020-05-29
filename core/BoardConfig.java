package Spiel.core;

import Spiel.XY;

import java.util.Arrays;
import java.util.List;

public class BoardConfig {
    private final int LENGTH = 40;
    private final int WIDTH = 80;
    private final XY SIZE = new XY(WIDTH,LENGTH);
    private final int ENTITIES_ON_BOARD = 500;
    private final List<String> BOT_NAMES = Arrays.asList("bot1","bot2");
    private final int MOVES = 100;
    private final int TIMEOUT = 40;

    public XY getSize() {
        return SIZE;
    }

    public int getLength() {
        return LENGTH;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getEntitiesOnBoard() {
        return ENTITIES_ON_BOARD;
    }

    public int getMaxEntities() {
        int WALL_COUNT = (LENGTH * 2 + WIDTH * 2) - 4;
        return WALL_COUNT + ENTITIES_ON_BOARD;
    }

    public List<String> getBotNames() {
        return BOT_NAMES;
    }

    public int getMoves() {
        return MOVES;
    }

    public int getTimeout() {
        return TIMEOUT;
    }
}
