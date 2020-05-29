package Spiel.unittests;

import Spiel.XY;
import Spiel.core.Board;
import Spiel.core.BoardConfig;
import Spiel.core.FlattenedBoard;
import Spiel.entities.HandOperatedMasterSquirrel;
import Spiel.entities.MasterSquirrel;
import Spiel.entities.MiniSquirrel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {

    private Board boardTest;
    private FlattenedBoard flattenedBoardTest;
    private MasterSquirrel masterTest;
    private MiniSquirrel miniTest;
    private HandOperatedMasterSquirrel handOpTest;
    private XY miniLoc;
    private XY masterLoc;
    private XY handOpLoc;

    @BeforeEach
    void setUp() {
        masterLoc = new XY(4,4);
        miniLoc = new XY(5,6);
        handOpLoc = new XY(7,7);

        boardTest = new Board(new FakeBoardConfig(), true);
    }

    @AfterEach
    void tearDown() {
        boardTest = null;
        flattenedBoardTest = null;
        masterTest = null;
        miniTest = null;
        handOpTest = null;
    }

    @Test
    void testNextStep() {
        /* insert Entities */
        masterTest = new MasterSquirrel(masterLoc);
        miniTest = new MiniSquirrel(miniLoc, 100, masterTest);
        handOpTest = new HandOperatedMasterSquirrel(handOpLoc, 3);
        boardTest.getEntitySet().add(handOpTest);
        boardTest.getEntitySet().add(masterTest);
        boardTest.getEntitySet().add(miniTest);
        int mini_energy = miniTest.getEnergy();

        /* print board before */
        flattenedBoardTest = boardTest.flatten();
        System.out.println(flattenedBoardTest.toString());

        /* save current locations */
        masterLoc = masterTest.getLocation();
        miniLoc = miniTest.getLocation();
        handOpLoc = handOpTest.getLocation();

        /* nextStepAll */
        boardTest.nextStepAll();

        /* print board after */
        flattenedBoardTest = boardTest.flatten();
        System.out.println(flattenedBoardTest.toString());

        /* assertion: if nextStepAll is called, the locations of the entities must have changed */
        assertTrue(masterLoc.getX() != masterTest.getLocation().getX() || masterLoc.getY() != masterTest.getLocation().getY());
        assertTrue(miniLoc.getX() != miniTest.getLocation().getX() || miniLoc.getY() != miniTest.getLocation().getY());

        /* assertion: handOp is not moved => location is same */
        assertFalse(handOpLoc.getX() != handOpTest.getLocation().getX() || handOpLoc.getY() != handOpTest.getLocation().getY());

        /* assertion: miniSquirrel looses 1 life point after moving */
        assertEquals(mini_energy-1, miniTest.getEnergy());
    }

    private static class FakeBoardConfig extends BoardConfig {
        private final int LENGTH = 15;
        private final int WIDTH = 15;
        private final XY SIZE = new XY(WIDTH,LENGTH);
        private final int ENTITIES_ON_BOARD = 40;

        @Override
        public int getLength() {
            return this.LENGTH;
        }

        @Override
        public int getWidth() {
            return this.WIDTH;
        }
    }
}
