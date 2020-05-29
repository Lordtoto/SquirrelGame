package Spiel;

import java.util.Random;

public class XYsupport {
    public static XY randomVector() {
        Random r = new Random();
        int x1 = r.nextInt(3) - 1;
        int y1 = r.nextInt(3) - 1;
        if (x1 == 0 && y1 == 0) {
            return randomVector();
        }
        return new XY(x1, y1);
    }

    private static int getRandomX(int upperLimit) {
        Random r = new Random();
        return r.nextInt(upperLimit-1) + 1;
    }

    private static int getRandomY(int upperLimit) {
        Random r = new Random();
        return r.nextInt(upperLimit-1) + 1;
    }

    public static XY getRandomLoc(int upperLimitX, int upperLimitY) {
        return new XY(getRandomX(upperLimitX),getRandomY(upperLimitY));
    }

    public static XY closeDistance(XY start, XY destination) {
        int moveX = (int)Math.signum(destination.getX() - start.getX());
        int moveY = (int)Math.signum(destination.getY() - start.getY());
        return (new XY(moveX, moveY));
    }
}
