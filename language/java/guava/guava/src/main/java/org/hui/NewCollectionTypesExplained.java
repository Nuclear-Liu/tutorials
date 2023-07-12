package org.hui;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NewCollectionTypesExplained {
    private static final String PARAGRAPH = "This is awkward, prone to mistakes, and doesn't support collecting a variety of useful statistics, like the total number of words. We can do better.";

    public static void main(String[] args) {
        String[] words = PARAGRAPH.split(" ");
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            Integer count = counts.get(word);
            if (count == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, count + 1);
            }
        }
        System.out.println(counts);

        Multiset<String> wordMultiset = HashMultiset.create(Arrays.asList(words));
        System.out.println(wordMultiset);
        wordMultiset.stream().collect(Collectors.toSet()).forEach(e -> {
            System.out.println(e + ":" + wordMultiset.count(e));
        });
        System.out.println(wordMultiset.entrySet());
        wordMultiset.add("of");
        wordMultiset.add("of", 10);
        System.out.println("---------------------------");
        wordMultiset.entrySet().forEach(
                entry -> System.out.println(String.format("%s:%s", entry.getElement(), entry.getCount())));
        System.out.println(wordMultiset.elementSet());
    }
}
