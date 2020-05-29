package Spiel.entities;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.core.EntityContext;

public class GoodBeast extends Character {
    private static final int initial_energy = 200;
    private int moveCounter = 0;

    public GoodBeast(XY location) {
        super(location, initial_energy);
    }

    public GoodBeast() {
    }

    public int getMoveCounter() {
        return this.moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    @Override
    public void nextStep() {
        this.newLocation(getLocation().randomMove());
    }

    @Override
    public void reset() {
        this.setEnergy(initial_energy);
    }

    @Override
    public void nextStep(EntityContext entityContext) {
        if (moveCounter >= 4) {
            Entity nearestPlayerEntity = entityContext.nearestPlayerEntity(this.getLocation());
            if (nearestPlayerEntity == null) {
                entityContext.tryMove(this, XYsupport.randomVector());
            } else {
                int moveX = -XYsupport.closeDistance(this.getLocation(), nearestPlayerEntity.getLocation()).getX();
                int moveY = -XYsupport.closeDistance(this.getLocation(), nearestPlayerEntity.getLocation()).getY();
                entityContext.tryMove(this, new XY(moveX, moveY));
            }
            moveCounter = 0;
        } else moveCounter++;
    }
}