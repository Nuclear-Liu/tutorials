package org.hui;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Comparator;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

public class CommonObjectUtilitiesExplained {
    public static void main(String[] args) {
        System.out.println(Objects.equal("a", "a"));
        System.out.println(Objects.equal(null, "a"));
        System.out.println(Objects.equal("a", null));
        System.out.println(Objects.equal(null, null));
        System.out.println(java.util.Objects.equals("a", "a"));
        System.out.println(java.util.Objects.equals(null, "a"));
        System.out.println(java.util.Objects.equals("a", null));
        System.out.println(java.util.Objects.equals(null, null));

        ToStringObj obj = new ToStringObj();
        System.out.println(obj);
        String objToString = MoreObjects.toStringHelper(ToStringObj.class)
                .add("add1", 1)
                .add("add2", 2)
                .toString();
        System.out.println(objToString);
    }
}
enum Color {
    READ,BLUE,GRAY
}
class Foo implements Comparable<Foo> {
    private String aString;
    private int anInt;
    private Color anEnum;

    private static final Comparator<Foo> COMPARATOR =
            comparing((Foo foo) -> foo.aString)
                    .thenComparing(foo -> foo.anInt)
                    .thenComparing(foo -> foo.anEnum, nullsLast(naturalOrder()));
    @Override
    public int compareTo(Foo o) {
        return ComparisonChain.start()
                .compare(aString,o.aString)
                .compare(anInt,o.anInt)
                .compare(anEnum,o.anEnum, Ordering.natural().nullsLast())
                .result();
    }

}

class ToStringObj {
    private String name;
    private int age;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("age", age)
                .toString();
    }
}
