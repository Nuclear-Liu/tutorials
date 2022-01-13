package org.hui.java.tutorials;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Hui.Liu
 * @since 2022-01-13 22:49
 */
public class NameSortTest {
    @Test
    public void testNameSort() {
        Name[] nameArray = {
                new Name("John", "Smith"),
                new Name("Karl", "Ng"),
                new Name("Jeff", "Smith"),
                new Name("Tom", "Rich")
        };

        List<Name> names = Arrays.asList(nameArray);
        System.out.println(names);

        Collections.sort(names);

        System.out.println(names);
    }
}
