package org.hui;

import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
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
        System.out.println("---------------------------");

        // creates a ListMultimap with tree keys and array list values
        ListMultimap<String, Integer> treeListMultimap = MultimapBuilder.treeKeys().arrayListValues().build();
        // creates a SetMultimap with hash keys and enum set values
        SetMultimap<String, MyEnum> treeSetMultimap = MultimapBuilder.hashKeys().enumSetValues(MyEnum.class).build();

        // Multimaps.toMultimap(String,Integer,)
        ListMultimap<@Nullable Object, @Nullable Object> objectObjectLinkedListMultimap = LinkedListMultimap.create();
        Set<MyEnum> h = treeSetMultimap.get("H");
        List<Integer> h1 = treeListMultimap.get("H");
        h1.add(23);
        // treeListMultimap.put()
        Collection<Map.Entry<String, Integer>> entries = treeListMultimap.entries();
        Set<Map.Entry<String, MyEnum>> entries1 = treeSetMultimap.entries();
    }
}
enum MyEnum {

}
