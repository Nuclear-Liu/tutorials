package org.hui.tdd.lombok.features.equalsandhashcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hui.Liu
 * @since 2021-12-30 11:21
 */
@EqualsAndHashCode(callSuper = true)
public class EqualsAndHashCodeExample extends AbstractClass {
    private transient int transientVar = 10;
    private float nanfloat = Float.NaN;
    private double nonDouble = Double.NaN;
    private String name;
    private double score;
    @EqualsAndHashCode.Exclude private Shape shape = new Shape(5, 10);
    private String[] args;
    @EqualsAndHashCode.Exclude private int id;

    public String getName() {
        return this.name;
    }
    @AllArgsConstructor
//    @EqualsAndHashCode(callSuper = true)
    public static class Shape {
        private int x;
        private int y;

    }

    public static void main(String[] args) {
        float nanF = Float.NaN;
        double nanD = Double.NaN;
        System.out.println(nanF == nanD);
        System.out.println(Double.NaN == Float.NaN);
        System.out.println(Double.NaN == Double.NaN);
    }

}

