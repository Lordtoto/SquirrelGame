package Spiel.core;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.botapi.BotController;
import Spiel.botapi.BotControllerFactory;
import Spiel.botapi.ControllerContext;
import Spiel.botapi.impl.bot2.BotControllerFactoryImpl;
import Spiel.entities.EntityType;
import Spiel.entities.MasterSquirrel;
import Spiel.entities.MiniSquirrel;

public class MiniSquirrelBot extends MiniSquirrel {

    private final BotController miniBotController;
    private int moves;

    MiniSquirrelBot(XY location, int energy, MasterSquirrel master, int moves) {
        super(location, energy, master);
        BotControllerFactory botControllerFactory = new BotControllerFactoryImpl();
        miniBotController = botControllerFactory.createMiniBotController();
        this.moves = moves;
    }


    @Override
    public void nextStep(EntityContext entityContext) {
        this.setMove(this.getMove()+1);
        if (getEnergy() <= 0) {
            entityContext.kill(this);
            return;
        }
        if (timeoutCheck()) {
            miniBotController.nextStep(new ControllerContextImpl(entityContext));
            updateEnergy(-1);
        }
    }

    public class ControllerContextImpl implements ControllerContext {

        private final EntityContext entityContext;

        ControllerContextImpl(EntityContext entityContext) {
            this.entityContext = entityContext;
        }

        @Override
        public XY getViewLowerLeft() {
            XY view = this.locate().plus(new XY(-10, -10));
            return checkIfInBounds(view);
        }

        private XY checkIfInBounds(XY xy) {
            XY size = entityContext.getSize();
            if (xy.getX() > size.getX()) {
                return checkIfInBounds(xy.plus(new XY(-1,0)));
            } else if (xy.getY() > size.getY()) {
                return checkIfInBounds(xy.plus(new XY(0,-1)));
            } else if (xy.getX() < 0) {
                return checkIfInBounds(xy.plus(new XY(1,0)));
            } else if (xy.getY() < 0) {
                return checkIfInBounds(xy.plus(new XY(0,1)));
            }
            return xy;
        }


        @Override
        public XY getViewUpperRight() {
            XY view = this.locate().plus(new XY(10, 10));
            return checkIfInBounds(view);
        }



        @Override
        public EntityType getEntityAt(XY xy) {
            if (inView(xy)) {
                return entityContext.getEntityType(xy);
            }
            return null;
        }

        @Override
        public boolean isMine(XY xy) {
            return entityContext.testMaster(xy, MiniSquirrelBot.this.getLocation());
        }

        private boolean inView(XY xy) {
            return xy.getX() >= getViewLowerLeft().getX() && xy.getX() <= getViewUpperRight().getX()
                    && xy.getY() >= getViewLowerLeft().getY() && xy.getY() <= getViewUpperRight().getY();
        }

        @Override
        public void move(XY direction) {
            entityContext.tryMove(MiniSquirrelBot.this, direction);
        }


        @Override
        public void spawnMiniBot(XY direction, int energy) {
        }

        @Override
        public void implode(int impactRadius) {
            if (impactRadius >= 2 && impactRadius <= 10) {
                entityContext.implode(MiniSquirrelBot.this, impactRadius);
            }
        }

        @Override
        public int getEnergy() {
            return MiniSquirrelBot.this.getEnergy();
        }

        @Override
        public XY directionOfMaster() {
            return XYsupport.closeDistance(locate(), MiniSquirrelBot.this.getMaster().getLocation());
        }

        @Override
        public long getRemainingSteps() {
            return moves - getMove();
        }

        @Override
        public void shout(String text) {
            System.out.println(text);
        }

        @Override
        public XY locate() {
            return MiniSquirrelBot.this.getLocation();
        }
    }
}
