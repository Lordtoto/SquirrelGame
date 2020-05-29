package Spiel.core;

import Spiel.XY;
import Spiel.entities.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FlattenedBoard implements EntityContext, BoardView {
    private Board board;
    private Entity[][] field;
    private static final Logger LOGGER = Logger.getLogger(FlattenedBoard.class.getName());

    {
        LOGGER.setLevel(Level.OFF);
    }

    public FlattenedBoard(Board board) {
        this.board = board;
        this.field = board.getField();
    }

    public FlattenedBoard(){}

    @Override
    public XY getSize() {
        return new XY(field.length,field[1].length);
    }

    public Entity[][] getField() {
        return field;
    }

    @Override
    public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
        XY newLoc = miniSquirrel.getLocation().plus(moveDirection);
        Entity locEntity = getEntity(newLoc);
        if (moveToEmpty(miniSquirrel, newLoc)) {
            return;
        }
        if (squirrelMeetEntity(miniSquirrel, locEntity)) {}
        else if (locEntity instanceof PlayerEntity) {
            meetOtherSquirrel((PlayerEntity)locEntity, miniSquirrel);
        }
    }

    @Override
    public void tryMove(GoodBeast goodBeast, XY moveDirection) {
        XY newLoc = goodBeast.getLocation().plus(moveDirection);
        moveToEmpty(goodBeast, newLoc);
    }

    public boolean moveToEmpty (Entity entity, XY newLoc) {
        if (getEntity(newLoc) == null) {
            move(entity, newLoc);
            return true;
        }
        return false;
    }

    private void move(Entity entity, XY newLoc) {
        field[entity.getLocation().getX()][entity.getLocation().getY()] = null;
        entity.setLocation(newLoc);
        field[newLoc.getX()][newLoc.getY()] = entity;
    }

    private Entity getEntity (XY pos) {
        return field[pos.getX()][pos.getY()];
    }

    @Override
    public void tryMove(BadBeast badBeast, XY moveDirection) {
        XY newLoc = badBeast.getLocation().plus(moveDirection);
        Entity locEntity =  getEntity(newLoc);
        if (moveToEmpty(badBeast, newLoc))
            return;
        if (locEntity instanceof PlayerEntity) {
            hitBadBeast(badBeast, (PlayerEntity) locEntity);
        }
    }

    private void hitBadBeast(BadBeast badBeast, PlayerEntity playerEntity) {
        if (badBeast.bite(playerEntity)) {
            killAndReplace(badBeast);
        }
    }

    @Override
    public void tryMove(MasterSquirrel master, XY moveDirection) {
        XY newLoc = master.getLocation().plus(moveDirection);
        Entity locEntity = getEntity(newLoc);
        if (moveToEmpty(master, newLoc)) {
            return;
        }
        if (squirrelMeetEntity(master, locEntity)) {}
        else if (locEntity instanceof MiniSquirrel) {
            meetOtherSquirrel(master, (MiniSquirrel) locEntity);
            moveToEmpty(master, newLoc);
        }
    }

    private void meetOtherSquirrel(PlayerEntity other, MiniSquirrel mini){
        /* wenn other ein MasterSquirrel ist */
        if (other instanceof MasterSquirrel) {
            /* und der master von mini other ist */
            if (((MasterSquirrel)other).isMaster(mini)) {
                other.updateEnergy(mini.getEnergy());
                /* wenn minis Master nicht other ist dann master energy + 150 */
            } else {
                other.updateEnergy(150);
            }
        } else {
            if (mini.getMaster().equals(((MiniSquirrel)other).getMaster())) {
                return;
            }
            kill(other);
        }
        kill(mini);
    }

    @Override
    public PlayerEntity nearestPlayerEntity(XY pos) {
        int distance = 6;
        Entity nearestPlayerEntity = null;
        for (Entity entity: board.getEntitySet().getContainer()) {
            if (entity instanceof PlayerEntity) {
                if (entity.getLocation().distanceFrom(pos) <= distance) {
                    nearestPlayerEntity = entity;
                    distance = (int) entity.getLocation().distanceFrom(pos);
                }
            }
        }
        return (PlayerEntity) nearestPlayerEntity;
    }

    private boolean squirrelMeetEntity (PlayerEntity squirrel, Entity locEntity) {
        XY newLoc = locEntity.getLocation();
        if (squirrel.equals(locEntity)) {
            return true;
        }
        if (locEntity instanceof Wall) {
            squirrel.updateEnergy(locEntity.getEnergy());
            squirrel.squirrelMeetsWall();
            return true;
        }
        if (locEntity instanceof GoodPlant || locEntity instanceof BadPlant || locEntity instanceof GoodBeast) {
            squirrel.updateEnergy(locEntity.getEnergy());
            killAndReplace(locEntity);
            move(squirrel, newLoc);
            return true;
        }
        if (locEntity instanceof BadBeast) {
            hitBadBeast((BadBeast) locEntity, squirrel);
            moveToEmpty(squirrel, newLoc);
            return true;
        }
        return false;
    }

    @Override
    public void kill(Entity entity) {
        field[entity.getLocation().getX()][entity.getLocation().getY()] = null;
        board.getEntitySet().removeEntity(entity);
        LOGGER.info(entity.toString() + " killed.");
    }

    @Override
    public void killAndReplace(Entity entity) {
        field[entity.getLocation().getX()][entity.getLocation().getY()] = null;
        entity.reset();
        XY loc = board.getRandomUnusedLoc();
        move(entity, loc);
        LOGGER.info(entity.toString() + " killed. New loc: " + loc.getX() + "|" + loc.getY());
    }

    public EntityType getEntityType (XY location) {
        return getEntityType(location.getX(), location.getY());
    }

    public void insertEntity(Entity entity) {
        board.getEntitySet().add(entity);
        field[entity.getLocation().getX()][entity.getLocation().getY()] = entity;
    }

    @Override
    public EntityType getEntityType(int x, int y) {
        if (field[x][y] == null) return EntityType.NONE;
        if (field[x][y] instanceof BadBeast) return EntityType.BadBeast;
        if (field[x][y] instanceof GoodBeast) return EntityType.GoodBeast;
        if (field[x][y] instanceof BadPlant) return EntityType.BadPlant;
        if (field[x][y] instanceof GoodPlant) return EntityType.GoodPlant;
        if (field[x][y] instanceof MasterSquirrel) return EntityType.MasterSquirrel;
        if (field[x][y] instanceof MiniSquirrel) return EntityType.MiniSquirrel;
        if (field[x][y] instanceof Wall) return EntityType.Wall;
        return null;
    }

    private boolean ifInBounds(XY xy) {
        XY size = getSize();
        return xy.getX() <= size.getX() && xy.getY() <= size.getY() && xy.getX() >= 0 && xy.getY() >= 0;
    }

    @Override
    public void implode(MiniSquirrel mini, int impactRadius) {
        double impactArea = impactRadius * impactRadius * Math.PI;
        int collectedEnergy = 0;
        for (Entity entity : board.getEntitySet().getContainer()) {
            if (entity == null)
                continue;
            int distance = (int) mini.getLocation().distanceFrom(entity.getLocation());
            if (distance <= impactRadius) {
                int energyLoss = (int) (200 * (mini.getEnergy() / impactArea) * (1 - distance / impactRadius));
                if (entity instanceof GoodBeast || entity instanceof GoodPlant || entity instanceof PlayerEntity) {
                    if (mini.getMaster().equals(entity)) {
                        continue;
                    }
                    if (entity instanceof MiniSquirrel) {
                        if (mini.getMaster().equals(((MiniSquirrel) entity).getMaster())) {
                            continue;
                        }
                    }
                    if (energyLoss < entity.getEnergy()) {
                        collectedEnergy += energyLoss;
                        entity.updateEnergy(-energyLoss);
                    } else {
                        if (entity instanceof GoodPlant || entity instanceof GoodBeast) {
                            collectedEnergy += entity.getEnergy();
                            killAndReplace(entity);
                        } else {
                            entity.updateEnergy(-energyLoss);
                            collectedEnergy += energyLoss;
                        }
                    }
                    LOGGER.info(EntityType.MiniSquirrel + " imploded and affected " + entity.getClass().getSimpleName() +
                            " damage: " + energyLoss + " Energy updated: " + entity.getEnergy());
                }
                if (entity instanceof BadBeast || entity instanceof BadPlant) {
                    if (energyLoss < -entity.getEnergy()) {
                        entity.updateEnergy(energyLoss);
                        collectedEnergy += energyLoss;
                    } else {
                        collectedEnergy += -entity.getEnergy();
                        entity.setEnergy(0);
                    }
                    LOGGER.info(EntityType.MiniSquirrel + " imploded and affected " + entity.getClass().getSimpleName() +
                            " damage: " + energyLoss + " Energy updated: " + entity.getEnergy());
                }
            }
        }
        mini.getMaster().updateEnergy(collectedEnergy);
        kill(mini);
    }

    @Override
    public boolean testMaster(XY locMaster, XY locMini) {
        return ((MasterSquirrelBot) field[locMaster.getX()][locMaster.getY()]).isMaster(field[locMini.getX()][locMaster.getY()]);
    }

    @Override
    public boolean tryInsertMini(MiniSquirrel mini) {
        if (getEntityType(mini.getLocation()) == EntityType.NONE) {
            insertEntity(mini);
            return true;
        }
        return false;
    }


    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int j = field.length-1; j >= 0; j--) {
            for (int i = 0; i < field.length; i++) {
                if (field[i][j] == null) {
                    s.append("      " + "    ");
                } else {
                    s.append((getEntityType(new XY(i, j)).toString() + "  "), 0, 6).append("    ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}