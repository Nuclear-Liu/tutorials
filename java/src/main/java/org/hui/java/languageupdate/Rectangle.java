package org.hui.java.languageupdate;

import java.util.function.BiFunction;

/**
 * @author Hui.Liu
 * @since 2021-12-20 10:02
 */
public record Rectangle(
        @GreaterThanZero double length,
        @GreaterThanZero double width) {

    // Nested record class
    record RotationAnge(double angle) {
        public RotationAnge {
            angle = Math.toRadians(angle);
        }
    }

    // public instance method
    public Rectangle getRotatedRectangleBoundingBox(double angle) {
        RotationAnge ra = new RotationAnge(angle);
        double x = Math.abs(length * Math.cos(ra.angle())) +
                Math.abs(width * Math.sin(ra.angle()));
        double y = Math.abs(length * Math.sin(ra.angle()) +
                Math.abs(width * Math.cos(ra.angle())));
        return new Rectangle(x, y);
    }

    // static field
    static double goldenRation;

    // static initializer
    static {
        goldenRation = (1 + Math.sqrt(5)) / 2;
    }

    // field declaration must be static;
    static BiFunction<Double, Double, Double> diagonal;

    // instance initializers are not allowed in records
    static {
        diagonal = (x, y) -> Math.sqrt(x * x + y * y);
    }

    public Rectangle {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(String.format("Invalid dimensions: %f, %f", length, width));
        }
    }

    @Override
    public double length() {
        System.out.println("Length is " + length);
        return length;
    }
}
