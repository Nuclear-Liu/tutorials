package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * @author BadMan
 * @since 2021-11-08 13:08
 */
public class TextMatcherTest {
    @Test
    public void testEqualToIgnoringCase() {
        assertThat("", "abc", equalToIgnoringCase("abc"));
        assertThat("", "abc", equalToIgnoringCase("aBC"));
        assertThat("", "abc", not(equalToIgnoringCase("a b c\t")));
    }
    @Test
    public void testEqualToIgnoringWhiteSpace() {
        assertThat("", "   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"));
        assertThat("", "   my\tfoo  bar ", equalToCompressingWhiteSpace(" my  foo bar"));
    }
    @Test
    public void testContainsString() {
        assertThat("", "abcde", containsString("bcd"));
    }
    @Test
    public void testEndsWith() {
        assertThat("", "abcd", endsWith("cd"));
    }
    @Test
    public void testStartsWith() {
        assertThat("", "abcd", startsWith("ab"));
    }
}
