package org.hui;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class OrderingExplained {
    public static void main(String[] args) {
        Ordering<Comparable> natural = Ordering.natural();
        Ordering<Object> objectOrdering = Ordering.usingToString();
        Ordering<Object> objectOrdering1 = Ordering.from(String.CASE_INSENSITIVE_ORDER)
                .onResultOf(Functions.toStringFunction());
        List<String> strs = Arrays.asList("c", "Y", "B", "a");
        strs.stream().sorted(objectOrdering1).forEach(System.out::println);
        strs.stream().sorted(Ordering.natural().lexicographical().onResultOf(Arrays::asList)).forEach(System.out::println);

        Ordering.natural().nullsFirst().onResultOf(StoredObj::getSortedBy);

    }
}

class StoredObj {
    @Nullable
    private String sortedBy;
    private int notSortedBy;

    @Nullable
    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(@Nullable String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public int getNotSortedBy() {
        return notSortedBy;
    }

    public void setNotSortedBy(int notSortedBy) {
        this.notSortedBy = notSortedBy;
    }
}
