package org.hui.middleware.lombok.features;

/**
 * Point Class.
 *
 * @author Hui.Liu
 * @since 2021-12-17 21:37
 */
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
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

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }
}
