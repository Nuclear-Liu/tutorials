package org.hui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ImmutableCollectionsExplained {
    public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet
            .of("red","orange","yellow","green","blue","purple");

    public static void main(String[] args) {
        List<String> list = Arrays.asList("test1", "test2","test3");
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        System.out.println(unmodifiableList);
        list.set(1,"badHui");
        System.out.println(unmodifiableList);
        List<String> strs = Arrays.asList("test1", "test2","test3");
        // ImmutableList<List<String>> immuStrs = ImmutableList.of(strs);
        // System.out.println(immuStrs);
        // strs.add(1, "badHuiiiii"); // UnsupportedOperationException
        // System.out.println(immuStrs);

        ImmutableList<String> immutStrs = ImmutableList.copyOf(strs);
        System.out.println(immutStrs);
//        strs.add(1, "badHuiiiii");
//        System.out.println(immutStrs);
        ImmutableList<String> strsByBuild = ImmutableList.<String>builder()
                .add("abc", "def")
                .addAll(Arrays.asList("hij", "uvw"))
                .build();
        System.out.println(strsByBuild);

        ImmutableSet<String> strSet = ImmutableSet.of("a", "b", "c", "a", "b", "d");
        System.out.println(strSet);
        thing(strSet);

        ImmutableSortedSet<Integer> numSet = ImmutableSortedSet.of(11, 23, 444, 56, 323, 12, 4, 8, 5, 7);
        System.out.println(numSet.asList());
        System.out.println(numSet.asList().get(5));


    }
    static void thing(Collection<String> collection) {
        ImmutableList<String> strList = ImmutableList.copyOf(collection);
        System.out.println(strList);
    }
}
