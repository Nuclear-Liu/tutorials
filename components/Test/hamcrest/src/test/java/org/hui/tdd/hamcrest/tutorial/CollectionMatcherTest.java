package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.array;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;

/**
 * @author BadMan
 * @since 2021-11-07 13:24
 */
public class CollectionMatcherTest {
    private final String[] arrayS = {"1", "2", "3", "4"};
    @Test
    public void testArray() {
        assertThat("", new String[]{"a", "b", "c"}, array(is("a"), is("b"), is("c")));
    }
    @Test(expected = AssertionError.class)
    public void testFailArray() {
        assertThat("", new String[]{"a", "b", "c"}, array(is("a"), is("B"), is("c")));
    }
    @Test
    public void testHasEntry() {
        Map<Integer, Biscuit> biscuits = getBiscuitsMap();
        assertThat("", biscuits, hasEntry(2, new Biscuit("blue")));
    }
    @Test
    public void testHasKey() {
        Map<Integer, Biscuit> biscuits = getBiscuitsMap();
        assertThat("", biscuits, hasKey(2));
    }
    @Test
    public void testHasValue() {
        Map<Integer, Biscuit> biscuits = getBiscuitsMap();
        assertThat("", biscuits, hasValue(new Biscuit("block")));
    }
    @Test
    public void testHasItemInArray() {
        List<Biscuit> biscuits = getBiscuitsList();
        assertThat("", biscuits.toArray(), hasItemInArray(new Biscuit("blue")));
    }
    private static Map<Integer, Biscuit> getBiscuitsMap() {
        Map<Integer,Biscuit> biscuits = new HashMap();
        biscuits.put(1, new Biscuit("red"));
        biscuits.put(2, new Biscuit("blue"));
        biscuits.put(3, new Biscuit("block"));
        return biscuits;
    }
    private static List<Biscuit> getBiscuitsList() {
        List<Biscuit> biscuits = new ArrayList<>();
        biscuits.add(new Biscuit("red"));
        biscuits.add(new Biscuit("blue"));
        biscuits.add(new Biscuit("block"));
        return biscuits;
    }
}
