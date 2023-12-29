package org.hui;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ReflectionExplained {
    public static void main(String[] args) {
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> integerList = Lists.newArrayList();
        System.out.println(stringList.getClass().isAssignableFrom(integerList.getClass()));
        System.out.println("a".getClass().isAssignableFrom(Integer.valueOf(4).getClass()));

        TypeToken<String> stringTok = TypeToken.of(String.class);
        System.out.println("string token:");
        System.out.println(stringTok);
        TypeToken<Integer> integerTok = TypeToken.of(Integer.class);
        System.out.println("integer token:");
        System.out.println(integerTok);

        TypeToken<?> tok = new TypeToken<Object>() {};
        System.out.println("object token:");
        System.out.println(tok);

        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
        };
        System.out.println("string list token:");
        System.out.println(stringListTok);

        TypeToken<Map<?, ?>> mapTok = new TypeToken<Map<?, ?>>() {
        };
        System.out.println("map tok");
        System.out.println(mapTok);

        TypeToken<Map<String, BigInteger>> mapTk = mapToken(
                TypeToken.of(String.class),
                TypeToken.of(BigInteger.class));
        System.out.println("map token func:");
        System.out.println(mapTk);
        TypeToken<Map<Integer, Queue<String>>> complexTok = mapToken(
                TypeToken.of(Integer.class),
                new TypeToken<Queue<String>>() {});
        System.out.println("complex token:");
        System.out.println(complexTok);

        System.out.println(ReflectionExplained.<String, BigInteger>incorrectMapToken());

        TypeToken<String> type = new IType<String>() {}.type;
        System.out.println(type);

        System.out.println(TypeToken.of(integerList.getClass()));

        System.out.println(new IType<List<Integer>>() {
        }.type);
    }

    static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {
        }
                .where(new TypeParameter<K>() {
                }, keyToken)
                .where(new TypeParameter<V>() {
                }, valueToken);
    }
    static <K, V> TypeToken<Map<K, V>> incorrectMapToken() {
        return new TypeToken<Map<K, V>>() {};
    }

}
abstract class IType<T> {
    TypeToken<T> type = new TypeToken<T>(getClass()) {};
}