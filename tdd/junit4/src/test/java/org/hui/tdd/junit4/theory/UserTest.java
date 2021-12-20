package org.hui.tdd.junit4.theory;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * @author Hui.Liu
 * @since 2021-11-19 20:46
 */
@RunWith(Theories.class)
public class UserTest {
    @DataPoint
    public static String GOOG_USERNAME = "optimus";
    @DataPoint
    public static String USERNAME_WITH_SLASH = "optimus/prime";

    @Theory
    public void filenameIncludesUsername(String username) {
        assumeThat(username, not(containsString("/")));
//        assertThat(new User(username).configFileName(), containsString(username));
    }
}
