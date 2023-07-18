package org.hui;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.*;

import static com.google.common.collect.Comparators.max;
import static java.util.Arrays.asList;

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

        System.out.println("------------ Lists ----------------");
        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp);
        System.out.println(countUp);
        System.out.println(countDown);
        List<List<Integer>> parts = Lists.partition(countUp, 2);
        System.out.println(parts);

        System.out.println("------------ Comparators ----------------");
        int[] iArr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(Ints.max(iArr));
        System.out.println(Ints.min(iArr));
        Comparator<String> strCmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };
        System.out.println(max("abc", "aba"));
        System.out.println(Collections.max(asList("aaa", "abbb", "abc")));
        System.out.println(max("abc", "abaa", strCmp));
        System.out.println(Collections.max(asList("aaa", "abbb", "abc"), strCmp));

        System.out.println("------------ Sets ----------------");
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        System.out.println(intersection);
        Sets.SetView<String> union = Sets.union(primes, wordsWithPrimeLength);
        System.out.println(union);
        Sets.SetView<String> difference = Sets.difference(wordsWithPrimeLength, primes);
        System.out.println(difference);
        Sets.SetView<String> symmetricDifference = Sets.symmetricDifference(primes, wordsWithPrimeLength);
        System.out.println(symmetricDifference);
        Set<List<String>> lists = Sets.cartesianProduct(wordsWithPrimeLength, primes);
        System.out.println(lists);
        Set<Set<String>> powerSet = Sets.powerSet(wordsWithPrimeLength);
        System.out.println("******");
        powerSet.forEach(System.out::println);

        System.out.println("------------ Maps ----------------");
        List<String> strings = Arrays.asList("one", "two1", "three", "six222");
        ImmutableMap<Integer, String> stringByIndex = Maps.uniqueIndex(strings, String::length);
        System.out.println(stringByIndex);

        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b",2,"c",4,"d",5);
        MapDifference<String, Integer> diff = Maps.difference(left, right);
        System.out.println(diff.entriesInCommon());
        System.out.println(diff.entriesDiffering());
        System.out.println(diff.entriesOnlyOnLeft());
        System.out.println(diff.entriesOnlyOnRight());

        BiMap<String,Integer> nameAge= HashBiMap.create();
        nameAge.put("hui",22);
        nameAge.put("wan",26);
        BiMap<String, Integer> synchronizedBiMap = Maps.synchronizedBiMap(nameAge);
        System.out.println(synchronizedBiMap);
        BiMap<String, Integer> unmodifiabledBiMap = Maps.unmodifiableBiMap(nameAge);
        System.out.println(unmodifiabledBiMap);

    }
}
