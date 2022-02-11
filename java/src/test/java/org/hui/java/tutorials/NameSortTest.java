package org.hui.java.tutorials;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Hui.Liu
 * @since 2022-01-13 22:49
 */
public class NameSortTest {
    private static Name[] nameArray = {
            new Name("John", "Smith", LocalDate.of(2009, 12, 21)),
            new Name("Karl", "Ng", LocalDate.of(2001, 12,11)),
            new Name("Jeff", "Smith", LocalDate.of(2001, 12,21)),
            new Name("Tom", "Rich", LocalDate.of(2001, 12,9))
    };

    @Test
    public void testNameSort() {

        List<Name> names = Arrays.asList(nameArray);
        System.out.println(names);

        Collections.sort(names);

        System.out.println(names);
    }

    @Test
    public void testSort() {
        Comparator<Name> orderDate = new Comparator<>() {
            @Override
            public int compare(Name o1, Name o2) {
                return o1.birthday().compareTo(o2.birthday());
            }
        };
        List<Name> names = Arrays.asList(nameArray);
        System.out.println(names);
        Collections.sort(names, orderDate);
        System.out.println(names);
    }
    @Test
    public void testSort2() {
        Comparator<Name> orderDate = (o1, o2) -> {
                return o1.birthday().compareTo(o2.birthday());
            };
        List<Name> names = Arrays.asList(nameArray);
        System.out.println(names);
        Collections.sort(names, orderDate);
        System.out.println(names);
    }
    @Test
    public void testSort3() {

        List<Name> names = Arrays.asList(nameArray);
        System.out.println(names);
        Collections.sort(names, NameSort.orderDate);
        System.out.println(names);
    }

    @Test
    public void test() {
        System.out.println(-Integer.MIN_VALUE == Integer.MIN_VALUE);
    }

    @Test
    public void test22() {
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
    }
}
