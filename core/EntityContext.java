package Spiel.core;

import Spiel.XY;
import Spiel.entities.*;

public interface EntityContext {

    void tryMove(MiniSquirrel miniSquirrel, XY moveDirection);
    void tryMove(GoodBeast goodBeast, XY moveDirection);
    void tryMove(BadBeast goodBeast, XY moveDirection);
    void tryMove(MasterSquirrel master, XY moveDirection);
    PlayerEntity nearestPlayerEntity(XY pos);
    void kill(Entity entity);
    void killAndReplace(Entity entity);
    EntityType getEntityType(XY xy);
    boolean moveToEmpty(Entity entity, XY loc);
    XY getSize();
    void implode(MiniSquirrel mini, int impactRadius);
    boolean testMaster(XY locMaster, XY locMini);
    public boolean tryInsertMini(MiniSquirrel mini);
}
