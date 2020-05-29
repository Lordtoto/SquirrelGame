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
import static org.junit.jupiter.api.Assertions.assertSame;

class MiniSquirrelTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private MiniSquirrel test;
    private XY testXy;
    private XY testXy2;
    private MasterSquirrel masterTest;

    @BeforeEach
    void setUp() {
        testXy = new XY(2,2);
        testXy2 = new XY(4,4);
        masterTest = new FakeMasterSquirrel(testXy);
        test = new MiniSquirrel(testXy2,10,masterTest);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        testXy = null;
        testXy2 = null;
        masterTest = null;
        test = null;
    }

    @Test
    void getMaster() {
        assertSame(test.getMaster(), masterTest);
    }

    @Test
    void nextStep_If1() {
        EntityContext fakeEntityContext = new FakeEntityContextImpl();
        test.setEnergy(10);
        test.setTimeout(0);
        test.nextStep(fakeEntityContext);

        assertEquals("tryMove MiniSquirrel", outContent.toString(), "if energy > 0 and timeout == 0 must be true");
    }

    @Test
    void nextStep_If2() {
        EntityContext fakeEntityContext = new FakeEntityContextImpl();
        test.setTimeout(1);
        test.setEnergy(0);
        test.nextStep(fakeEntityContext);

        assertEquals("kill", outContent.toString(), "if energy <= 0 and timeout > 0 must be true");
    }

    private static class FakeMasterSquirrel extends MasterSquirrel {

        public FakeMasterSquirrel(XY location) {
            super(location);
        }
    }

    private static class FakeEntityContextImpl implements EntityContext {

        @Override
        public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
            System.out.print("tryMove MiniSquirrel");
        }

        @Override
        public void tryMove(GoodBeast goodBeast, XY moveDirection) {

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
            System.out.print("kill");
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