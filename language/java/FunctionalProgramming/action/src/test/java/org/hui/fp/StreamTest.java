package org.hui.fp;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class StreamTest {

    @Test void testToList() {
        Stream<String> is = Stream.<String>builder().add("hello").add("test").build();
        List<String> list = is.toList();
    }
    @Test void testFlatMap() {
        List<String> l1 = Arrays.asList("hello", "tes");
        List<String> l2 = Arrays.asList("hello", "tes");
        Stream<List<String>> stream = Stream.of(l1, l2);
        List<String> collect = stream.flatMap(List::stream).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test void testCount() {
        List<String> l1 = Arrays.asList("hello", "tes");
        List<String> l2 = Arrays.asList("hello", "tes");

        Stream<List<String>> stream = Stream.of(l1, l2);
        System.out.println(stream.flatMap(List::stream).count());
    }

    @Test void testReduce() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        Integer result = stream.reduce(1, (r, e) -> r * e);
        System.out.println(result);
    }
    @Test void testMapToInt() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        IntStream intStream = stream.mapToInt(Integer::intValue);
    }
    @Test void testMapToLong() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        LongStream longStream = stream.mapToLong(Integer::longValue);
    }
    @Test void testMapToDouble() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        DoubleStream doubleStream = stream.mapToDouble(Integer::doubleValue);
    }
}
