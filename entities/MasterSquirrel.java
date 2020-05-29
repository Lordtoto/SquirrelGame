package Spiel.entities;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.core.EntityContext;

public class MasterSquirrel extends PlayerEntity {
    private String name;
    private static final int initial_energy = 1000;
    private MiniSquirrel nextMini;
    private int move = 0;
    public MasterSquirrel(XY location) {
        super(location, initial_energy);
    }

    public MasterSquirrel() {
    }

     @Override
     public void nextStep() {
         this.newLocation(getLocation().randomMove());
     }

    public boolean isMaster(Entity test) {
        if (test instanceof MiniSquirrel) {
            return ((MiniSquirrel) test).getMaster() == this;
        }
        return false;
    }

    public void setNextMini(MiniSquirrel nextMini) {
        this.nextMini = nextMini;
    }

    public MiniSquirrel getNextMini() {
        return nextMini;
    }

    public void createMiniSquirrel(int energy) {
        this.updateEnergy(-energy);
        setNextMini(new MiniSquirrel(this.getLocation(), energy, this));
    }

    public void nextStep(EntityContext entityContext) {
        if (timeoutCheck()) {
            entityContext.tryMove(this, XYsupport.randomVector());
        }
    }

    public void reset(XY loc) {
        this.setEnergy(initial_energy);
        this.setLocation(loc);
        this.setMove(0);
    }

    public String getName() {
        return this.name;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }
}
