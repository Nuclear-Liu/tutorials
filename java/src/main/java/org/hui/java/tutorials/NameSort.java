package org.hui.java.tutorials;

import java.util.Comparator;

/**
 * @author Hui.Liu
 * @since 2022-01-14 21:11
 */
public interface NameSort {

    Comparator<Name> orderDate = (o1, o2) -> {
        return o1.birthday().compareTo(o2.birthday());
    };
}
