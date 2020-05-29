package Spiel.unittests;

import Spiel.XY;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class XYTest {

    private XY xyTest1;
    private XY xyTest2;

    @BeforeEach
    void setUp() {
        xyTest1 = new XY(1,1);
        xyTest2 = new XY(2,2);
    }

    @AfterEach
    void tearDown() {
        xyTest1 = null;
        xyTest2 = null;
    }

    @Test
    void testGetX() {
        int x1 = xyTest1.getX();
        int x2 = xyTest2.getX();

        int exp1 = 1;
        int exp2 = 2;

        assertEquals(exp1, x1);
        assertEquals(exp2,x2);
    }

    @Test
    void testGetY() {
        int y1 = xyTest1.getY();
        int y2 = xyTest2.getY();

        int exp1 = 1;
        int exp2 = 2;

        assertEquals(exp1, y1);
        assertEquals(exp2, y2);
    }

    @Test
    void testEquals() {
        XY test = new XY(1, 1);
        assertTrue(test.equals(xyTest1));
    }

    @Test
    void testDistanceFrom() {
        XY test = new XY(4, 2);
        double dist1 = test.distanceFrom(xyTest1);
        double dist2 = test.distanceFrom(xyTest2);

        double exp1 = Math.sqrt(Math.pow(3, 2) + 1);
        double exp2 = 2.0;

        assertEquals(exp1, dist1, 0.0);
        assertEquals(exp2, dist2, 0.0);
    }

    @Test
    void testPlus() {
        XY test = xyTest1.plus(xyTest2);
        int out_x = test.getX();
        int out_y = test.getY();

        int exp_x = 3;
        int exp_y = 3;

        assertEquals(exp_x, out_x);
        assertEquals(exp_y, out_y);
    }

    @Test
    void testMinus() {
        XY test = xyTest2.minus(xyTest1);
        int out_x = test.getX();
        int out_y = test.getY();

        int exp_x = 1;
        int exp_y = 1;

        assertEquals(exp_x, out_x);
        assertEquals(exp_y, out_y);
    }

    @Test
    void testTimes() {
        XY test = xyTest2.times(3);
        int out_x = test.getX();
        int out_y = test.getY();

        int exp_x = 6;
        int exp_y = 6;

        assertEquals(exp_x, out_x);
        assertEquals(exp_y, out_y);
    }
}