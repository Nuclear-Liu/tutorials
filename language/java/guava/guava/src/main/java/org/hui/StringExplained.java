package org.hui;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.Arrays;

public class StringExplained {
    public static void main(String[] args) {
        Joiner joiner = Joiner.on("; ").skipNulls();
        System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));

        System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));

        Iterable<String> split = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux");
        System.out.println(split);
    }
}
