package org.hui.tdd.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * @author Hui.Liu
 * @since 2021-11-16 13:21
 */
@RunWith(Parameterized.class)
public class StringUtilTest {
    private String str;

    public StringUtilTest(String str) {
        this.str = str;
    }

    @Parameterized.Parameters(name = "{index}:isNotBlank({0})")
    public static Iterable<? extends String> data() {
        return Arrays.asList("first test", "second test");
    }

    @Test
    public void test() {
        assertTrue(StringUtil.isNotBlank(str));
    }

}
