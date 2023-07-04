package org.hui;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImmutableList<String> list = new ImmutableList.Builder<String>().build();
        List<Object> list2 = Collections.unmodifiableList(Lists.newArrayList());

        Optional<Integer> possible = Optional.of(5);
        if (possible.isPresent()) {
            System.out.println(possible.get());
        }
        Optional<String> absent = Optional.absent();
        System.out.println(absent.isPresent());
//        System.out.println(absent.get());

        String a = null;
        Optional<String> isNull = Optional.fromNullable(a);
        System.out.println(isNull.isPresent());
        a = "hello";
        Optional<String> nonNull = Optional.fromNullable(a);
        System.out.println(nonNull.isPresent());
        System.out.println(nonNull.get());

        Optional<String> fromJavaUtil = Optional.fromJavaUtil(java.util.Optional.of("5"));
        System.out.println(fromJavaUtil.isPresent());
        Optional<String> zero = Optional.of("0");
        Optional<String> orValue = fromJavaUtil.or(zero);
        System.out.println(orValue);
        Optional<String> orIsNull = isNull.or(zero);
        System.out.println(orIsNull);

    }
}