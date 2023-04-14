package org.hui.tdd.hamcrest.tutorial;

import java.util.Objects;

/**
 * @author BadMan
 * @since 2021-11-06 16:20
 */
public class Biscuit {
    private String color;

    public Biscuit(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biscuit biscuit = (Biscuit) o;
        return color.equals(biscuit.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "Biscuit{" +
                "color='" + color + '\'' +
                '}';
    }
}
