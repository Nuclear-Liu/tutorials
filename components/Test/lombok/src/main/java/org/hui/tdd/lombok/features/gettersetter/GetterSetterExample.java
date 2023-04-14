package org.hui.tdd.lombok.features.gettersetter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hui.Liu
 * @since 2021-12-22 20:03
 */
public class GetterSetterExample {
    @Getter @Setter private int age;
    @Setter(AccessLevel.PROTECTED) private String name;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private String taste;

    @Override
    public String toString() {
        return String.format("%s (age: %d)", name, age);
    }
}
