package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Hui.Liu
 * @since 2021-11-17 22:29
 */
public class UsesErrorCollectorTwice {
    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test(expected = Throwable.class)
    public void example() {
//        collector.addError(new Throwable("first thing went wrong"));
//        collector.addError(new Throwable("second thing went wrong"));
//        collector.checkThat(getResult(), not(containsString("ERROR!")));
    }
}
