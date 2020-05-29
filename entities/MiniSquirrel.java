package Spiel.entities;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.core.EntityContext;

public class MiniSquirrel extends PlayerEntity {
    private MasterSquirrel master;
    private int move = 0;
    public MiniSquirrel(XY location, int energy, MasterSquirrel master) {
        super(location, energy);
        this.master = master;
    }

    public MiniSquirrel() {
    }

    public MasterSquirrel getMaster() {
        return master;
    }

    public void setMaster(MasterSquirrel master) {
        this.master = master;
    }

    public void nextStep(EntityContext entityContext) {
        if (getEnergy() <= 0) {
            entityContext.kill(this);
            return;
        }
        if (timeoutCheck()) {
            entityContext.tryMove(this, XYsupport.randomVector());
            updateEnergy(-1);
        }
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
}