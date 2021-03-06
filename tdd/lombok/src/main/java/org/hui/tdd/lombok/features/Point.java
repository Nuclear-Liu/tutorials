package org.hui.tdd.lombok.features;

/**
 * Point Class.
 *
 * @author Hui.Liu
 * @since 2021-12-17 21:37
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // can not add Override annotation.
    // @Override
//    public boolean equals(Point other) {
//        return (this.getX() == other.getX() && this.getY() == other.getY());
//    }

    /*@Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }*/

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (that.canEqual(this) && this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }

    public boolean canEqual(Object other) {
        return (other instanceof Point);
    }
}
