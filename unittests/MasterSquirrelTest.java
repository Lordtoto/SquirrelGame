package Spiel.unittests;

import Spiel.XY;
import Spiel.botapi.SpawnException;
import Spiel.core.EntityContext;
import Spiel.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class MasterSquirrelTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private MasterSquirrel test;

    @BeforeEach
    void setUp() {
        test = new MasterSquirrel(new XY(1,1));
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        test = null;
    }

    @Test
    void isMaster() {
        test.createMiniSquirrel(100);
        Entity miniSquirrel = test.getNextMini();
        assertTrue(test.isMaster(miniSquirrel));
    }


    @Test
    void createMiniSquirrel() {
        test.createMiniSquirrel(100);

        assertEquals(100, test.getNextMini().getEnergy());
        assertEquals(test, test.getNextMini().getMaster());
    }

    @Test
    void nextStep() throws SpawnException {
        EntityContext entityContextImplMock = new FakeFlattenedBoard();
        test.nextStep(entityContextImplMock);
        assertEquals("tryMove MasterSquirrel", outContent.toString());
    }

    /* Mock class of EntityContextImplementation */
    private static class FakeFlattenedBoard implements EntityContext {

        @Override
        public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {

        }

        @Override
        public void tryMove(GoodBeast goodBeast, XY moveDirection) {

        }

        @Override
        public void tryMove(BadBeast goodBeast, XY moveDirection) {

        }

        @Override
        public void tryMove(MasterSquirrel master, XY moveDirection) {
            System.out.print("tryMove MasterSquirrel");
        }

        @Override
        public PlayerEntity nearestPlayerEntity(XY pos) {
            return null;
        }

        @Override
        public void kill(Entity entity) {

        }

        @Override
        public void killAndReplace(Entity entity) {

        }

        @Override
        public EntityType getEntityType(XY xy) {
            return null;
        }

        @Override
        public boolean moveToEmpty(Entity entity, XY loc) {
            return false;
        }


        @Override
        public XY getSize() {
            return null;
        }

        @Override
        public void implode(MiniSquirrel mini, int impactRadius) {

        }

        @Override
        public boolean testMaster(XY locMaster, XY locMini) {
            return false;
        }

        @Override
        public boolean tryInsertMini(MiniSquirrel mini) {
            return true;
        }
    }
}