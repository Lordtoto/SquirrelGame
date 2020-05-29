package Spiel.entities;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.core.EntityContext;

import java.util.Scanner;
import java.util.logging.Logger;

import static Spiel.XY.*;

public class HandOperatedMasterSquirrel extends MasterSquirrel {
    private final String name = "handOperatedMaster";
    private XY nextMove = new XY(0,0);
    private static final Logger LOGGER = Logger.getLogger(HandOperatedMasterSquirrel.class.getName());

    public HandOperatedMasterSquirrel (XY location, int id) {
        super(location);
    }

    public void nextStep() {
        System.out.println("Zug:");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 1:
                newLocation(getLocation().plus(LEFT));
                break;
            case 2:
                newLocation(getLocation().plus(RIGHT));
                break;
            case 3:
                newLocation(getLocation().plus(UP));
                break;
            case 4:
                newLocation(getLocation().plus(DOWN));
                break;
            default:
                System.out.println("Falsche Eingabe");
                this.nextStep();
        }
        nextMove = ZERO_ZERO;
    }

    public void nextStep(EntityContext entityContext) {
        if (timeoutCheck()) {
            if (this.getNextMini() != null) {
                XY targetLoc = getNextMini().getMaster().getLocation().plus(XYsupport.randomVector());
                for (int i = 0; i < 30 && !entityContext.tryInsertMini(getNextMini()); i++) {
                    getNextMini().setLocation(getNextMini().getMaster().getLocation().plus(XYsupport.randomVector()));
                }
                LOGGER.info("spawn " + getNextMini().toString());
                getNextMini().setTimeout(1);
                this.setNextMini(null);
            } else {
                entityContext.tryMove(this, nextMove);
            }
        }
    }

    public void setNextMove (XY move) {
       nextMove = move;
    }

    @Override
    public String getName() {
        return name;
    }
}