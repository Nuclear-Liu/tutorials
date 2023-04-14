package org.hui.java.concurrencyprogramming.c001ordering;

import org.openjdk.jol.info.ClassLayout;

public class Test {
    public static void main(String[] args) {
        Object o = new Object();

        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);

//        o.hashCode();
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
