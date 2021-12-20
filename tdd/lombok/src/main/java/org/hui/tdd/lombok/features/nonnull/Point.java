package org.hui.tdd.lombok.features.nonnull;

import lombok.Data;
import lombok.NonNull;

/**
 * @author Hui.Liu
 * @since 2021-12-19 17:21
 */
public class Point {
    private Integer x;
    private Integer y;

    public Integer getX() {
        return x;
    }

    @NonNull
    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(@NonNull Integer y) {
        this.y = y;
    }
}
