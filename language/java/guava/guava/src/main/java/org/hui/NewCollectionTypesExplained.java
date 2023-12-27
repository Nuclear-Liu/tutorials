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

        System.out.println("------------ BiMap ---------------");
        Map<String, Integer> nameToId = Maps.newHashMap();
        Map<Integer,String> idToName = Maps.newHashMap();

        nameToId.put("Bob",42);
        idToName.put(42,"Bob");

        BiMap<Integer, String> idName = HashBiMap.create();
        idName.put(42,"Bob");
        BiMap<String, Integer> inverse = idName.inverse();
        idName.put(12, "badHui");
        System.out.println(inverse.get("badHui"));
        inverse.put("Hui",28);
        inverse.put("Hui",29);
        System.out.println(idName);
        // idName.remove(12);
        // idName.put(13, "badHui");
        idName.forcePut(13,"badHui");
        System.out.println(inverse);

        System.out.println("------------ Table ---------------");
        Table<Integer, Integer, Double> table = HashBasedTable.create();
        table.put(0,0, 1.0);
        table.put(0,1, 3.14);
        table.put(1,0,22.62);
        Map<Integer, Double> row = table.row(0);
        Map<Integer, Double> column = table.column(1);
        System.out.println(table);
        Map<Integer, Map<Integer, Double>> integerMapMap = table.rowMap();
        Set<Integer> integers = table.rowKeySet();
        System.out.println(integers);
        Set<Table.Cell<Integer, Integer, Double>> cells = table.cellSet();
        System.out.println(cells);

        System.out.println("------------ ClassToInstanceMap ---------------");
        ClassToInstanceMap<Number> numberDefaults = MutableClassToInstanceMap.create();
        numberDefaults.putInstance(Integer.class, 0);
        numberDefaults.putInstance(Double.class, 3.14);
        numberDefaults.putInstance(Number.class, 99);
        System.out.println(numberDefaults);

        System.out.println("------------ RangeSet ---------------");
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1,10));
        System.out.println(rangeSet);
        rangeSet.add(Range.closedOpen(11, 15));
        System.out.println(rangeSet);
        rangeSet.add(Range.closedOpen(15,20));
        System.out.println(rangeSet);
        rangeSet.add(Range.openClosed(0,0));
        System.out.println(rangeSet);
        rangeSet.remove(Range.open(5,10));
        System.out.println(rangeSet);

        System.out.println("------------ RangeMap ---------------");
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1,10),"foo"); // [[1..10]=foo]
        System.out.println(rangeMap);
        rangeMap.put(Range.open(3, 6), "bar"); // [[1..3]=foo, (3..6)=bar, [6..10]=foo]
        System.out.println(rangeMap);
        rangeMap.put(Range.open(10,20),"foo"); // [[1..3]=foo, (3..6)=bar, [6..10]=foo, (10..20)=foo]
        System.out.println(rangeMap);
        rangeMap.remove(Range.closed(5, 11)); // [[1..3]=foo, (3..5)=bar, (11..20)=foo]
        System.out.println(rangeMap);
    }
}
enum MyEnum {

}
