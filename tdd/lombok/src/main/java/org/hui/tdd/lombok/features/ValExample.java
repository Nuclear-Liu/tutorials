package org.hui.tdd.lombok.features;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Hui.Liu
 * @since 2021-12-08 21:40
 */
public class ValExample {
    public String example() {
        val example = new ArrayList<String>();
        example.add("Hello, World");
        val foo = example.get(0);
        return foo.toLowerCase();
    }

    public void example2() {
        val map = new HashMap<Integer, String>();
        map.put(0, "zero");
        map.put(5, "five");
        for (val entry : map.entrySet()) {
            System.out.printf("%d: %s\n", entry.getKey(), entry.getValue());
        }
    }

    public void varExample() {
        var s = "Hello";
        s = "Hello, World";
    }
}
