package Spiel.unittests;

import Spiel.XY;
import Spiel.entities.PlayerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerEntityTest {

    private PlayerEntity test;
    private XY testXy;

    @BeforeEach
    void setUp() {
        int testEnergy = 1000;
        testXy = new XY(2,2);
        test = new PlayerEntity(testXy, testEnergy);
    }

    @AfterEach
    void tearDown() {
        test = null;
        testXy = null;
    }

    @Test
    void squirrelMeetsWall() {
        test.squirrelMeetsWall();

        assertEquals(3, test.getTimeout());
    }

    @Test
    void timeoutCheck() {
        test.setTimeout(1);

        assertFalse(test.timeoutCheck(), "if timeOut greater 0 must be false");
        assertEquals(0,test.getTimeout());
        assertTrue(test.timeoutCheck(), "if timeOut equals 0 must be true");

        test.setTimeout(0);

        assertTrue(test.timeoutCheck(), "if timeout equals 0 must be true");
    }
}