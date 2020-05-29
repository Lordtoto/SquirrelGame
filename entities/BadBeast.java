package Spiel.entities;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.core.EntityContext;

public class BadBeast extends Character {
    private static final int initial_energy = -150;
    private int biteCounter = 0;
    private int moveCounter = 0;

    public BadBeast(XY location) {
        super(location, initial_energy);
    }

    public BadBeast() {
    }

    public int getBiteCounter() {
        return this.biteCounter;
    }

    public void setBiteCounter(int biteCounter) {
        this.biteCounter = biteCounter;
    }

    public int getMoveCounter() {
        return this.moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    @Override
    public void reset() {
        this.biteCounter = 0;
        setEnergy(initial_energy);
    }

    @Override
    public void nextStep(EntityContext entityContext) {
        if (moveCounter >= 4) {
            Entity nearestPlayerEntity = entityContext.nearestPlayerEntity(this.getLocation());
            if (nearestPlayerEntity == null) {
                entityContext.tryMove(this, XYsupport.randomVector());
            } else {
                entityContext.tryMove(this, XYsupport.closeDistance(this.getLocation(), nearestPlayerEntity.getLocation()));
            }
            moveCounter = 0;
        } else moveCounter++;
    }

    public boolean bite(PlayerEntity playerEntity) {
        playerEntity.updateEnergy(initial_energy);
        biteCounter++;
        return biteCounter >= 7;
    }
}
