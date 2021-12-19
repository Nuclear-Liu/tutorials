package org.hui.tdd.lombok.features;

/**
 * @author Hui.Liu
 * @since 2021-12-18 16:49
 */
public class ColoredPoint extends Point { // Problem: equals not symmetric

    private final Color color;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    /*@Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColoredPoint) {
            ColoredPoint that = (ColoredPoint) other;
            result = (this.color.equals(that.color) && super.equals(that));
        } else if (other instanceof Point) {
            Point that = (Point) other;
            result = that.equals(this);
        }
        return result;
    }*/

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColoredPoint) {
            ColoredPoint that = (ColoredPoint) other;
            result = (that.canEqual(this) && this.color.equals(that.color) && super.equals(that));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (41 * super.hashCode() + color.hashCode());
    }

    @Override
    public boolean canEqual(Object other) {
        return (other instanceof ColoredPoint);
    }
}
