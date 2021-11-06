package org.hui.tdd.hamcrest.tutorial;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author BadMan
 * @since 2021-11-02 20:26
 */
public class IsNotANumber extends TypeSafeMatcher<Double> {

    @Override
    public boolean matchesSafely(Double number) {
        return number.isNaN();
    }

    public void describeTo(Description description) {
        description.appendText("not a number");
    }

    public static Matcher<Double> notANumber() {
        return new IsNotANumber();
    }
}
