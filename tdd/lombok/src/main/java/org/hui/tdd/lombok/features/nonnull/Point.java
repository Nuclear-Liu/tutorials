package org.hui.tdd.lombok.features.nonnull;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Hui.Liu
 * @since 2021-12-19 17:21
 */
@Setter
@Getter
public class Point {
    @NonNull
    private Integer x;
    private Integer y;

    public Point() {
    }

    @NonNull
    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static void testParameter(@NonNull String tests) {
        tests.equals("hello");
    }

    @NonNull
    public static void methodAnon() {
        "Hello".equals("Hello");
    }

    @NonNull
    public static String method1() {
        return null;
    }

    @NonNull
    public static String  method2(Object p1, String s2) {
        return p1.toString() + s2;
    }


    public static void localVariableIsNull() {
        @NonNull
        String o = method1();
        int i = 0;
        i ++;
    }


}
