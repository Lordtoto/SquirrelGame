package Spiel.unittests;

import Spiel.XY;
import Spiel.core.Board;
import Spiel.core.BoardConfig;
import Spiel.core.EntitySet;
import Spiel.core.FlattenedBoard;
import Spiel.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;


class FlattenedBoardTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private FakeFlattenedBoard test;
    private FakeMini fakeMini;
    private FakeMaster fakeMaster;
    private XY testXy0;
    private XY testXy1;
    private XY testXy2;
    private FakeBoardConfig fakeBoardConfig;
    private FakeGoodBeast fakeGoodBeast;
    private FakeBadBeast fakeBadBeast;
    private FakeGoodPlant fakeGoodPlant;
    private FakeBadPlant fakeBadPlant;
    private FakeWall fakeWall;

    @BeforeEach
    void setUp() {
        testXy0 = new XY(0,0);
        testXy1 = new XY(1, 1);
        testXy2 = new XY(2,2);
        fakeMaster = new FakeMaster();
        fakeGoodBeast = new FakeGoodBeast();
        fakeBadBeast = new FakeBadBeast();
        fakeGoodPlant = new FakeGoodPlant();
        fakeBadPlant = new FakeBadPlant();
        fakeWall = new FakeWall();
        fakeMini = new FakeMini();
        fakeBoardConfig = new FakeBoardConfig();
        FakeBoard fakeBoard = new FakeBoard();
        test = new FakeFlattenedBoard(fakeBoard);
        test.addToFieldAt(fakeMaster, testXy0);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        test = null;
        testXy0 = null;
        testXy2 = null;
        fakeMini = null;
        fakeMaster = null;
        fakeGoodBeast =  null;
        fakeBadBeast = null;
        fakeGoodPlant = null;
        fakeBadPlant = null;
    }


    @Test
    void testTryMoveMiniSquirrelOwnMaster() {
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        fakeMaster.setEnergy(200);
        test.addToFieldAt(fakeMini, testXy1);
        test.tryMove(fakeMini, new XY(-1,-1));
        assertNull("check Loc", test.getEntityAt(testXy1));
        assertEquals("check Energy Master", 300, fakeMaster.getEnergy());
        assertEquals("check Master", fakeMaster, test.getEntityAt(testXy0));
    }

    @Test
    void testTryMoveMiniSquirrelOtherMaster() {
        fakeMini.setEnergy(100);
        fakeMini.setMaster(new FakeMaster());
        fakeMaster.setEnergy(200);
        test.addToFieldAt(fakeMini, testXy1);
        test.tryMove(fakeMini, new XY(-1,-1));
        assertNull("check Loc", test.getEntityAt(testXy1));
        assertEquals("check Energy Master", 350, fakeMaster.getEnergy());
        assertEquals("check Master", fakeMaster, test.getEntityAt(testXy0));
    }

    @Test
    void testTryMoveMiniSquirrelOtherMini() {
        FakeMini fakeMini2 = new FakeMini();
        fakeMini2.setEnergy(100);
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        fakeMini2.setMaster(new FakeMaster());
        test.addToFieldAt(fakeMini, testXy0);
        test.addToFieldAt(fakeMini2, testXy1);
        test.tryMove(fakeMini, testXy1);
        assertNull("check Loc", test.getEntityAt(testXy0));
        assertNull("check other Mini", test.getEntityAt(testXy1));
    }

    @Test
    void testTryMoveMiniSquirrelBrotherMini() {
        FakeMini fakeMini2 = new FakeMini();
        fakeMini2.setEnergy(100);
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        fakeMini2.setMaster(fakeMaster);
        test.addToFieldAt(fakeMini, testXy0);
        test.addToFieldAt(fakeMini2, testXy1);
        test.tryMove(fakeMini, testXy1);
        assertEquals("check Loc", fakeMini, test.getEntityAt(testXy0));
        assertEquals("check Energy", 100, fakeMini.getEnergy());
        assertEquals("check other Mini", fakeMini2, test.getEntityAt(testXy1));
        assertEquals("check Energy other Mini", 100, fakeMini2.getEnergy());
    }

    @Test
    void testTryMoveGoodBeastEmpty() {
        test.addToFieldAt(fakeGoodBeast, testXy0);
        test.tryMove(fakeGoodBeast, testXy1);
        assertEquals(fakeGoodBeast, test.getEntityAt(testXy1));
    }

    @Test
    void testTryMoveGoodBeastNotEmpty() {
        test.addToFieldAt(fakeGoodBeast, testXy0);
        test.addToFieldAt(fakeBadBeast, testXy1);
        test.tryMove(fakeGoodBeast, testXy1);
        assertEquals(fakeGoodBeast, test.getEntityAt(testXy0));
    }

    @Test
    void testMoveToEmpty() {
        Entity[][] field = test.getField();
        boolean actual = test.moveToEmpty(fakeMaster, testXy1);
        assertTrue("if target space is empty must be true",actual);
        assertEquals(fakeMaster, test.getEntityAt(testXy1));
        field[2][2] = new FakeBadBeast();
        actual = test.moveToEmpty(fakeMaster, new XY (2, 2));
        assertFalse("if target space is not empty must be false",actual);
        assertEquals(fakeMaster, test.getEntityAt(testXy1));
    }

    @Test
    void testTryMoveBadBeast() {
        fakeMaster.setEnergy(200);
        fakeBadBeast.setEnergy(-150);
        test.addToFieldAt(fakeBadBeast, testXy1);
        test.tryMove(fakeBadBeast, new XY(-1,-1));
        assertEquals("check Loc", fakeBadBeast, test.getEntityAt(testXy1));
        assertEquals("check Energy", 50, fakeMaster.getEnergy());
        assertEquals("check Master", fakeMaster, test.getEntityAt(testXy0));
        assertEquals("Bite Counter", 1, fakeBadBeast.getBiteCounter());
    }

    @Test
    void testTryMoveMasterSquirrelEmpty() {
        test.addToFieldAt(fakeGoodBeast, testXy0);
        test.tryMove(fakeMaster, testXy1);
        assertEquals(fakeMaster, test.getEntityAt(testXy1));
    }

    @Test
    void testTryMoveMasterSquirrelWall() {
        fakeMaster.setEnergy(200);
        fakeWall.setEnergy(-10);
        test.addToFieldAt(fakeWall, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy0));
        assertEquals("check Energy", 190, fakeMaster.getEnergy());
        assertEquals("check Timeout", 3, fakeMaster.getTimeout());
    }

    @Test
    void testTryMoveMasterSquirrelGoodBeast() {
        fakeMaster.setEnergy(200);
        fakeGoodBeast.setEnergy(150);
        test.addToFieldAt(fakeGoodBeast, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy1));
        assertEquals("check Energy", 350, fakeMaster.getEnergy());
        assertEquals("check new GoodBeast", fakeGoodBeast, test.getEntityAt(testXy2));
    }

    @Test
    void testTryMoveMasterSquirrelGoodPlant() {
        fakeMaster.setEnergy(200);
        fakeGoodPlant.setEnergy(100);
        test.addToFieldAt(fakeGoodPlant, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy1));
        assertEquals("check Energy", 300, fakeMaster.getEnergy());
        assertEquals("check new GoodPlant", fakeGoodPlant, test.getEntityAt(testXy2));
    }

    @Test
    void testTryMoveMasterSquirrelBadPlant() {
        fakeMaster.setEnergy(200);
        fakeBadPlant.setEnergy(-100);
        test.addToFieldAt(fakeBadPlant, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy1));
        assertEquals("check Energy", 100, fakeMaster.getEnergy());
        assertEquals("check new BadPlant", test.getEntityAt(testXy2), fakeBadPlant);
    }

    @Test
    void testTryMoveMasterSquirrelBadBeast() {
        fakeMaster.setEnergy(200);
        fakeBadBeast.setEnergy(-150);
        test.addToFieldAt(fakeBadBeast, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy0));
        assertEquals("check Energy", 50, fakeMaster.getEnergy());
        assertEquals("check BadBeast", fakeBadBeast, test.getEntityAt(testXy1));
        assertEquals("Bite Counter", 1, fakeBadBeast.getBiteCounter());
    }

    @Test
    void testTryMoveMasterSquirrelMaster() {
        fakeMaster.setEnergy(200);
        FakeMaster fakeMaster2 = new FakeMaster();
        test.addToFieldAt(fakeMaster2, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy0));
        assertEquals("check Energy", 200, fakeMaster.getEnergy());
        assertEquals("check other Master", fakeMaster2, test.getEntityAt(testXy1));
    }

    @Test
    void testTryMoveMasterSquirrelOwnMini() {
        fakeMaster.setEnergy(200);
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        test.addToFieldAt(fakeMini, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy1));
        assertEquals("check Energy", 300, fakeMaster.getEnergy());
    }

    @Test
    void testTryMoveMasterSquirrelOtherMini() {
        fakeMaster.setEnergy(200);
        fakeMini.setEnergy(100);
        fakeMini.setMaster(new FakeMaster());
        test.addToFieldAt(fakeMini, testXy1);
        test.tryMove(fakeMaster, testXy1);
        assertEquals("check Loc", fakeMaster, test.getEntityAt(testXy1));
        assertEquals("check Energy", 350, fakeMaster.getEnergy());
    }


    @Test
    void testNearestPlayerEntity() {
        fakeMaster.setEnergy(200);
        fakeMini.setEnergy(100);
        test.addToFieldAt(fakeMini, testXy1);
        test.insertEntity(fakeMaster);
        test.insertEntity(fakeMini);
        assertEquals("Mini closer than Master", fakeMini, test.nearestPlayerEntity(testXy2));
        assertNull("No Entitys in range", test.nearestPlayerEntity(new XY(10,10)));
    }


    @Test
    void testGetEntityType() {
        Entity[][] field = test.getField();
        field[1][1] = fakeMaster;
        EntityType entityType = test.getEntityType(new XY(1, 1));
        assertEquals(fakeMaster.getClass().getSimpleName() ,EntityType.MasterSquirrel, entityType);
    }

    @Test
    void testImplodeMaster() {
        FakeMaster fakeMaster2 = new FakeMaster();
        test.addToFieldAt(fakeMaster2, testXy2);
        test.addToFieldAt(fakeMaster, testXy0);
        test.addToFieldAt(fakeMini, testXy1);
        test.insertEntity(fakeMaster2);
        test.insertEntity(fakeMaster);
        test.insertEntity(fakeMini);
        fakeMaster.setEnergy(200);
        fakeMaster2.setEnergy(200);
        fakeMini.setEnergy(1000);
        fakeMini.setMaster(fakeMaster);
        test.implode(fakeMini, 10);
        assertNull("check Loc", test.getEntityAt(testXy1));
        assertEquals("check Energy own Master", 836, fakeMaster.getEnergy());
        assertNull("check Loc", test.getEntityAt(testXy1));
        assertEquals("check Energy other Master", -436, fakeMaster2.getEnergy());
    }

    @Test
    void testImplodeMasterNegative() {
        FakeMaster fakeMaster2 = new FakeMaster();
        test.addToFieldAt(fakeMaster2, testXy2);
        test.addToFieldAt(fakeMaster, testXy0);
        test.addToFieldAt(fakeMini, testXy1);
        test.insertEntity(fakeMaster2);
        test.insertEntity(fakeMaster);
        test.insertEntity(fakeMini);
        fakeMaster.setEnergy(200);
        fakeMaster2.setEnergy(-200);
        fakeMini.setEnergy(1000);
        fakeMini.setMaster(fakeMaster);
        test.implode(fakeMini, 10);
        assertNull("check Loc", test.getEntityAt(testXy1));
        assertEquals("check Energy own Master", 836, fakeMaster.getEnergy());
        assertNull("check Loc", test.getEntityAt(testXy1));
        assertEquals("check Energy other Master", -836, fakeMaster2.getEnergy());
    }


    @Test
    void testImplodeGoodDie() {
        fakeMini.setEnergy(1000);
        fakeMini.setMaster(fakeMaster);
        fakeMaster.setEnergy(0);
        test.addToFieldAt(fakeMini, testXy0);
        test.insertEntity(fakeMini);
        test.addToFieldAt(fakeGoodBeast, testXy1);
        test.insertEntity(fakeGoodBeast);
        fakeGoodBeast.setEnergy(200);
        test.implode(fakeMini, 5);
        assertNull("check Loc", test.getEntityAt(testXy0));
        assertEquals("check Energy Master", 200, fakeMaster.getEnergy());
        assertEquals("check Good Loc", fakeGoodBeast, test.getEntityAt(testXy2));
        assertEquals("check Energy Good", 200, fakeGoodBeast.getEnergy());
    }

    @Test
    void testImplodeGoodSurvive() {
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        fakeMaster.setEnergy(0);
        test.addToFieldAt(fakeMini, testXy0);
        test.insertEntity(fakeMini);
        test.addToFieldAt(fakeGoodBeast, testXy1);
        test.insertEntity(fakeGoodBeast);
        fakeGoodBeast.setEnergy(200);
        test.implode(fakeMini, 10);
        assertNull("check Loc", test.getEntityAt(testXy0));
        assertEquals("check Energy Master", 63, fakeMaster.getEnergy());
        assertEquals("check Energy Good", 137, fakeGoodBeast.getEnergy());
        assertEquals("check Beast Loc", fakeGoodBeast, test.getEntityAt(testXy1));
    }

    @Test
    void testImplodeBadSurvive() {
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        fakeMaster.setEnergy(0);
        test.addToFieldAt(fakeMini, testXy0);
        test.insertEntity(fakeMini);
        test.addToFieldAt(fakeBadBeast, testXy1);
        test.insertEntity(fakeBadBeast);
        fakeBadBeast.setEnergy(-150);
        test.implode(fakeMini, 10);
        assertNull("check Loc", test.getEntityAt(testXy0));
        assertEquals("check Energy Master", 63, fakeMaster.getEnergy());
        assertEquals("check Energy Bad", -87, fakeBadBeast.getEnergy());
        assertEquals("check Beast Loc", fakeBadBeast, test.getEntityAt(testXy1));
    }

    @Test
    void testImplodeBadSurvive0() {
        fakeMini.setEnergy(100);
        fakeMini.setMaster(fakeMaster);
        fakeMaster.setEnergy(0);
        test.addToFieldAt(fakeMini, testXy0);
        test.insertEntity(fakeMini);
        test.addToFieldAt(fakeBadBeast, testXy1);
        test.insertEntity(fakeBadBeast);
        fakeBadBeast.setEnergy(0);
        test.implode(fakeMini, 10);
        assertNull("check Loc", test.getEntityAt(testXy0));
        assertEquals("check Energy Master", 0, fakeMaster.getEnergy());
        assertEquals("check Energy Bad", 0, fakeBadBeast.getEnergy());
        assertEquals("check Beast Loc", fakeBadBeast, test.getEntityAt(testXy1));
    }

    @Test
    void testImplodeBadDie() {
        fakeMini.setEnergy(1000);
        fakeMini.setMaster(fakeMaster);
        fakeMaster.setEnergy(0);
        test.addToFieldAt(fakeMini, testXy0);
        test.insertEntity(fakeMini);
        test.addToFieldAt(fakeBadBeast, testXy1);
        test.insertEntity(fakeBadBeast);
        fakeBadBeast.setEnergy(-150);
        test.implode(fakeMini, 5);
        assertNull("check Loc", test.getEntityAt(testXy0));
        assertEquals("check Energy Master", 150, fakeMaster.getEnergy());
        assertEquals("check Good Loc", fakeBadBeast, test.getEntityAt(testXy1));
        assertEquals("check Energy Bad", 0, fakeBadBeast.getEnergy());
    }


    /* Mocks and Stubs */

    class FakeBoard extends Board {

        public FakeBoard() {
            this.setBoardConfig(fakeBoardConfig);
            this.setEntitySet(new FakeEntitySet());
            this.setFlattenedBoard(test);
            this.flatten();
        }

        @Override
        public XY getRandomUnusedLoc() {
            return testXy2;
        }
    }

    static class FakeBoardConfig extends BoardConfig {

    }

    static class FakeMini extends MiniSquirrel {

        public FakeMini() {

        }
    }

    static class FakeMaster extends MasterSquirrel {

        public FakeMaster(XY location) {
            super(location);
        }

        public FakeMaster() {
        }
    }

    class FakeFlattenedBoard extends FlattenedBoard {

        public FakeFlattenedBoard(Board board) {
            super(board);
        }

        public void addToFieldAt(Entity entity, XY loc) {
            Entity[][] field = getField();
            entity.setLocation(loc);
            field[loc.getX()][loc.getY()] = entity;
        }

        public Entity getEntityAt (XY loc) {
            Entity[][] field = test.getField();
            return field[loc.getX()][loc.getY()];
        }
    }

    static class FakeEntitySet extends EntitySet {

    }

    static class FakeWall extends Wall {

        public FakeWall() {
        }

    }

    static class FakeBadBeast extends BadBeast {

        public FakeBadBeast() {
        }

    }

    static class FakeBadPlant extends BadPlant {

        public FakeBadPlant() {
        }
    }

    static class FakeGoodBeast extends GoodBeast {

        public FakeGoodBeast (XY location) {
            super(location);
        }

        public FakeGoodBeast() {
        }
    }

    static class FakeGoodPlant extends GoodPlant {

        public FakeGoodPlant() {
        }
    }
}