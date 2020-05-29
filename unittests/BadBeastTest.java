package Spiel.unittests;

import Spiel.XY;
import Spiel.core.EntityContext;
import Spiel.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BadBeastTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private BadBeast test;
    private XY testXy;
    private XY testXy2;

    @BeforeEach
    void setUp() {
        testXy2 = new XY(2,2);
        testXy = new XY(2,2);
        test = new BadBeast(testXy);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        testXy = null;
        test = null;
    }

    @Test
    void reset() {
        test.setBiteCounter(4);
        test.setEnergy(-10);
        test.reset();

        int initialEnergy = -150;

        assertEquals(initialEnergy, test.getEnergy());
        assertEquals(0, test.getBiteCounter());
    }

    @Test
    void nextStep() {
        EntityContext fakeEntityContext = new FakeEntityContextImpl();
        test.setMoveCounter(1);
        test.nextStep(fakeEntityContext);

        assertEquals(2, test.getMoveCounter(), "if moveCounter < 4 must be true");

        test.setMoveCounter(4);
        test.nextStep(fakeEntityContext);

        assertEquals(0, test.getMoveCounter(), "if moveCounter >= 4 must be true");

        test.nextStep(fakeEntityContext);

        assertEquals("tryMove BadBeast", outContent.toString());
    }

    @Test
    void bite() {
        int energy = 1000;
        PlayerEntity fakePlayerEntity = new FakePlayerEntity(testXy2, 10, energy);

        test.bite(fakePlayerEntity);

        assertEquals(energy + test.getEnergy(), fakePlayerEntity.getEnergy());
        assertEquals(1, test.getBiteCounter(), "if bite() while biteCounter equals 0 must be true");
        assertFalse(test.bite(fakePlayerEntity), "if biteCounter < 7 must be false");

        test.setBiteCounter(7);

        assertTrue(test.bite(fakePlayerEntity), "if biteCounter >= 7 must be true");

    }

    private static class FakePlayerEntity extends PlayerEntity {

        public FakePlayerEntity(XY location, int iD, int energy) {
            super(location, energy);
        }
    }

    /* Mock class of EntityContextImpl */
    private static class FakeEntityContextImpl implements EntityContext {

        @Override
        public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {

        }

        @Override
        public void tryMove(GoodBeast goodBeast, XY moveDirection) {

        }

        @Override
        public void tryMove(BadBeast goodBeast, XY moveDirection) {
            System.out.print("tryMove BadBeast");
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