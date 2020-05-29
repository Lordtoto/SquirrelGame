package Spiel.unittests;

import Spiel.XY;
import Spiel.core.EntityContext;
import Spiel.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoodBeastTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private GoodBeast test;
    private XY testXy;


    @BeforeEach
    void setUp() {
        testXy = new XY(2,2);
        test = new GoodBeast(testXy);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        test = null;
        testXy = null;
    }

    @Test
    void testReset() {
        test.setEnergy(10);
        test.reset();

        int initialEnergy = 200;

        assertEquals(initialEnergy, test.getEnergy());
    }

    @Test
    void testNextStep() {
        EntityContext fakeEntityContext = new FakeEntityContextImpl();
        test.setMoveCounter(1);
        test.nextStep(fakeEntityContext);

        assertEquals(2, test.getMoveCounter(), "if moveCounter < 4 must be true");

        test.setMoveCounter(4);
        test.nextStep(fakeEntityContext);

        assertEquals(0, test.getMoveCounter(), "if moveCounter >= 4 must be true");

        test.nextStep(fakeEntityContext);

        assertEquals("tryMove GoodBeast", outContent.toString());
    }

    /* Mock class of EntityContextImplementation */
    private static class FakeEntityContextImpl implements EntityContext {

        @Override
        public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {

        }

        @Override
        public void tryMove(GoodBeast goodBeast, XY moveDirection) {
            System.out.print("tryMove GoodBeast");
        }

        @Override
        public void tryMove(BadBeast goodBeast, XY moveDirection) {

        }

        @Override
        public void tryMove(MasterSquirrel master, XY moveDirection) {

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