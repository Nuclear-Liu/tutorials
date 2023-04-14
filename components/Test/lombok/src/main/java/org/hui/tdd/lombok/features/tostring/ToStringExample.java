package org.hui.tdd.lombok.features.tostring;

import lombok.ToString;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


/**
 * @author Hui.Liu
 * @since 2021-12-23 21:29
 */
@ToString(callSuper = false)
public class ToStringExample {
    private static final int STATIC_VAR = 10;
    @ToString.Include private static final int INCLUDE_FIELD = 10;
    private String name;
    private Square1 square1 = new Square1(5, 10);
    private Square2 square2 = new Square2(5, 10);
    private Square3 square3 = new Square3(5, 10);
    private Square4 square4 = new Square4(5, 10);
    private String[] tags;
    @ToString.Exclude private int id;


}
@ToString(includeFieldNames = true)
class Square1 {
    private final int width, height;

    public Square1(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
@ToString(includeFieldNames = false)
class Square2 {
    private final int width, height;

    public Square2(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
@ToString(callSuper = false, includeFieldNames = true)
class Square3 extends Square1 {
    private final int width, height;

    public Square3(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }
}
@ToString(callSuper = true, includeFieldNames = true)
class Square4 extends Square1 {
    private final int width, height;

    public Square4(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }
}