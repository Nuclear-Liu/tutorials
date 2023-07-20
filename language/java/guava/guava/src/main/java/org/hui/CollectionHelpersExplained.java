package org.hui;

import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionHelpersExplained {
    public static void main(String[] args) {
        //
        List<String> wrapList = wrap(new ArrayList<>(10));
        wrapList.add("two");
        wrapList.add("two");
        wrapList.add("two");
        wrapList.add("two");
        wrapList.add(2, "rrro");
        System.out.println(wrapList);

        System.out.println("----------- PeekingIterator -----------------");
        List<String> result = Lists.newArrayList();
        PeekingIterator<String> iter = Iterators.peekingIterator(result.iterator());
        while (iter.hasNext()) {
            // skip this duplicate element
            iter.next();
        }
        result.add("result");

        Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) {
            @Nullable
            @Override
            protected Integer computeNext(Integer previous) {
                return (previous == 1 << 30) ? null: previous * 2;
            }
        };
    }
    public static Iterator<String> skipNulls(final Iterator<String> in) {
        return new AbstractIterator<String>() {
            @Nullable
            @Override
            protected String computeNext() {
                while (in.hasNext()) {
                    String s = in.next();
                    if (s != null) {
                        return s;
                    }
                }
                return endOfData();
            }
        };
    }
    private static <T> List<T> wrap(final List<T> delegate) {
        return new AddLoggingList<>(delegate);
    }
}
class AddLoggingList<E> extends ForwardingList<E> {
    final List<E> delegate; // backing list

    AddLoggingList(List<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<E> delegate() {
        return delegate;
    }

    @Override
    public void add(int index, E element) {
        System.out.println(String.format("add index: %d,elem: %s", index, element));
        super.add(index, element);
    }

    @Override
    public boolean add(E element) {
        System.out.println(String.format("add elem: %s", element));
        return super.add(element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> elements) {
        System.out.println(String.format("add elems: %s", elements));
        return super.addAll(index, elements);
    }
}
