package org.hui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CollectionUtiltiesExplained {
    public static void main(String[] args) {
        List<String> strs1 = Lists.newArrayList();
        System.out.println(strs1);
        Map<Integer, String> strDic = Maps.newLinkedHashMap();
        System.out.println(strDic);
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
        System.out.println(theseElements);
        List<String> exactly100 = Lists.newArrayListWithCapacity(100);
        List<String> approx100 = Lists.newArrayListWithExpectedSize(100);

        System.out.println("------------ Iterables ----------------");
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6));
        // concatenated has elements 1, 2, 3, 4, 5, 6
        System.out.println(concatenated);
        Integer last = Iterables.getLast(concatenated);
        System.out.println(last);
        // Integer onlyElement = Iterables.getOnlyElement(concatenated);
        // System.out.println(onlyElement);
        Iterable<Integer> limit = Iterables.limit(concatenated, 2);
        System.out.println(limit);
        Iterable<Integer> unmodifiableIterable = Iterables.unmodifiableIterable(concatenated);
        System.out.println(unmodifiableIterable);
        System.out.println(Iterables.elementsEqual(concatenated, unmodifiableIterable));
        Iterable<Integer> concatenated2 = Iterables.concat(
                Ints.asList(1, 2, 4),
                Ints.asList(3, 5, 6));
        System.out.println(Iterables.elementsEqual(concatenated, concatenated2));
    }
}
