package Spiel.entities;

import Spiel.XY;
import Spiel.core.EntityContext;

public class PlayerEntity extends Character {
    private int timeout = 0;
    public PlayerEntity(XY location, int energy) {
        super(location, energy);
    }

    public PlayerEntity() {
    }

    @Override
    public void nextStep() { this.newLocation(getLocation().randomMove());
    }

    public void nextStep (EntityContext entityContext) {
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }


    public void squirrelMeetsWall() {
        timeout = 3;
    }

    public boolean timeoutCheck() {
        if (timeout > 0) {
            timeout--;
            return false;
        } else
            return true;
    }

    public int getTimeout() {
        return timeout;
    }
}