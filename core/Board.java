package Spiel.core;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.entities.*;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private FlattenedBoard flattenedBoard;
    private BoardConfig boardConfig;
    private EntitySet entitySet;
    private int freezeTimer = 0;

    public Board(BoardConfig boardConfig) {
        this.boardConfig = boardConfig;
        this.entitySet = new EntitySet();
        setBorder();
        for (int i = 0; i < (boardConfig.getEntitiesOnBoard()); i++) entitySetRandom();
        this.flattenedBoard = flatten();
    }

    public Board() {
        this.boardConfig = new BoardConfig();
        this.entitySet = new EntitySet();
        setBorder();
        this.flattenedBoard = flatten();
    }

    public Board(BoardConfig boardConfig, boolean test) {
        this.boardConfig = boardConfig;
        this.entitySet = new EntitySet();
        setBorder();
        this.flattenedBoard = flatten();
    }

    public void setFlattenedBoard(FlattenedBoard flattenedBoard) {
        this.flattenedBoard = flattenedBoard;
    }

    public void setEntitySet(EntitySet entitySet) {
        this.entitySet = entitySet;
    }

    public void setBoardConfig(BoardConfig boardConfig) {
        this.boardConfig = boardConfig;
    }

    public void freeze() {
        freezeTimer += 1;
    }

    public EntitySet getEntitySet() {
        return this.entitySet;
    }

    private void setBorder() {
        for (int i = 0; i < boardConfig.getWidth(); i++) {
            entitySet.add(new Wall(new XY(i, 0)));
            entitySet.add(new Wall(new XY(i, boardConfig.getLength() - 1)));
        }
        for (int i = 0; i < boardConfig.getLength() - 1; i++) {
            entitySet.add(new Wall(new XY(0, i)));
            entitySet.add(new Wall(new XY(boardConfig.getWidth() - 1, i)));
        }
    }

    public XY getRandomUnusedLoc() {
        XY location = XYsupport.getRandomLoc(boardConfig.getWidth(), boardConfig.getLength());
        while (entitySet.isLocUsed(location)) {
            location = XYsupport.getRandomLoc(boardConfig.getWidth(), boardConfig.getLength());
        }
        return location;
    }

    private Entity getRandomEntity() {
        XY location = getRandomUnusedLoc();
        Random r = new Random();
        int rand = r.nextInt(5) + 1;
        switch (rand) {
            case 1:
                return new BadBeast(location);
            case 2:
                return new GoodBeast(location);
            case 3:
                return new BadPlant(location);
            case 4:
                return new GoodPlant(location);
            case 5:
                return new Wall(location);
        }
        return null;
    }

    private void entitySetRandom() {
        entitySet.add(getRandomEntity());
    }

    public FlattenedBoard flatten() {
        return new FlattenedBoard(this);
    }

    public Entity[][] getField() {
        Entity[][] field = new Entity[boardConfig.getWidth()][boardConfig.getLength()];
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                field[x][y] = entitySet.searchByLocation(new XY(x, y));
            }
        }
        return field;
    }

    public HandOperatedMasterSquirrel getHandOperatedSquirrel() {
        for (Entity entity : entitySet.getContainer()) {
            if (entity instanceof HandOperatedMasterSquirrel) {
                return (HandOperatedMasterSquirrel) entity;
            }
        }
        return null;
    }

    public ArrayList<MasterSquirrel> getMasterSquirrel() {
        ArrayList<MasterSquirrel> array = new ArrayList<>();
        for (Entity entity : entitySet.getContainer()) {
            if (entity instanceof MasterSquirrel) {
                array.add((MasterSquirrel) entity);
            }
        }
        return array;
    }

    public String getMasterSquirrelString() {
        StringBuilder result = new StringBuilder();
        for (MasterSquirrel master : getMasterSquirrel()) {
            result.append(master.toString()).append("\n");
        }
        return result.toString();
    }

    public void nextStepAll() {
        if (freezeTimer >= 1) {
            freezeTimer--;
            return;
        }
       entitySet.nextStepAll(this.flattenedBoard);
    }

    public void setFreezeTimer(int freezeTimer) {
        this.freezeTimer = freezeTimer;
    }

    public BoardConfig getBoardConfig() {
        return boardConfig;
    }

    public ArrayList<MiniSquirrel> getMiniSquirrel() {
        ArrayList<MiniSquirrel> array = new ArrayList<>();
        for (Entity entity : entitySet.getContainer()) {
            if (entity instanceof MiniSquirrel) {
                array.add((MiniSquirrel) entity);
            }
        }
        return array;
    }

    public void deleteMinis() {
        for (MiniSquirrel mini : getMiniSquirrel()) {
            entitySet.getContainer().remove(mini);
        }
    }
}