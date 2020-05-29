package Spiel;

import java.util.Objects;

public final class XY {
    private final int x;
    private final int y;
    public static final XY UP = new XY(0,1);
    public static final XY DOWN = new XY(0,-1);
    public static final XY RIGHT = new XY(1,0);
    public static final XY LEFT = new XY(-1,0);
    public static final XY RIGHT_UP = new XY(1, -1);
    public static final XY RIGHT_DOWN = new XY(1, 1);
    public static final XY LEFT_UP = new XY(-1, -1);
    public static final XY LEFT_DOWN = new XY(-1, 1);
    public static final XY ZERO_ZERO = new XY(0,0);

    public XY() {
        x = 0;
        y = 0;
    }

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public XY randomMove() {
        return plus(XYsupport.randomVector());
    }

    public boolean equals(XY location) {
        if(location == null) return false;
        return (location.getX() == this.x) && (location.getY() == this.y);
    }

    public double distanceFrom(XY location) {
        return Math.sqrt(Math.pow((location.getX() - x), 2) + Math.pow((location.getY() - y), 2));
    }

    public XY plus(XY translationVector) {
        return new XY(x + translationVector.x, y + translationVector.y);
    }

    public XY minus(XY xy) {
        return new XY(this.x - xy.getX(), this.y - xy.getY());
    }

    public XY times(int factor) {
        return new XY(this.x * factor, this.y * factor);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XY xy = (XY) o;
        return x == xy.x &&
                y == xy.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}