package Spiel.core;

import Spiel.XY;
import Spiel.botapi.BotController;
import Spiel.botapi.BotControllerFactory;
import Spiel.botapi.ControllerContext;
import Spiel.botapi.SpawnException;
import Spiel.entities.Entity;
import Spiel.entities.EntityType;
import Spiel.entities.MasterSquirrel;

import java.lang.reflect.InvocationTargetException;

public class MasterSquirrelBot extends MasterSquirrel {

    private final BotController masterBotController;
    private  BotControllerFactory botControllerFactory;
    private final String botName;
    private int moves;

    public MasterSquirrelBot(XY location, String botName, int moves) {
        super(location);
        this.botName = botName;
        loadBotControllerFactory();
        masterBotController = botControllerFactory.createMasterBotController();
        this.moves = moves;
    }

    private void loadBotControllerFactory() {
        try {
            Class<?> clazz = Class.forName("Spiel.botapi.impl." + botName + ".BotControllerFactoryImpl");
            botControllerFactory = (BotControllerFactory) clazz.getConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return this.botName;
    }

    @Override
    public void nextStep(EntityContext entityContext) {
        this.setMove(this.getMove()+1);
        if (timeoutCheck()) {
            masterBotController.nextStep(new MasterSquirrelBot.ControllerContextImpl(entityContext));
        }
    }

    @Override
    public String toString() {
        return botName + ": " + super.toString();
    }

    public class ControllerContextImpl implements ControllerContext {

        private final EntityContext entityContext;

        ControllerContextImpl(EntityContext entityContext) {
            this.entityContext = entityContext;
        }

        @Override
        public XY getViewLowerLeft() {
            XY view = this.locate().plus(new XY(-15, -15));
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
            XY view = this.locate().plus(new XY(15, 15));
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
            return entityContext.testMaster(MasterSquirrelBot.this.getLocation(), xy);
        }

        private boolean inView(XY xy) {
            return xy.getX() >= getViewLowerLeft().getX() && xy.getX() <= getViewUpperRight().getX()
                    && xy.getY() >= getViewLowerLeft().getY() && xy.getY() <= getViewUpperRight().getY();
        }



        @Override
        public void move(XY direction) {
            entityContext.tryMove(MasterSquirrelBot.this, direction);
        }

        @Override
        public void spawnMiniBot(XY direction, int energy) throws SpawnException {
            if (MasterSquirrelBot.this.getEnergy() < energy) {
                System.out.println(MasterSquirrelBot.this.getEnergy());
                throw new SpawnException();
            }
            MasterSquirrelBot.this.updateEnergy(-energy);
            MiniSquirrelBot mini = new MiniSquirrelBot(MasterSquirrelBot.this.getLocation().plus(direction), energy, MasterSquirrelBot.this, moves);
            if (!entityContext.tryInsertMini(mini)) {
                System.out.println(getEntityAt(mini.getLocation()));
                throw new SpawnException();
            }
        }

        @Override
        @Deprecated
        public void implode(int impactRadius) {

        }

        @Override
        public int getEnergy() {
            return MasterSquirrelBot.this.getEnergy();
        }

        @Deprecated
        @Override
        public XY directionOfMaster() {
            return null;
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
            return MasterSquirrelBot.this.getLocation();
        }
    }
}
