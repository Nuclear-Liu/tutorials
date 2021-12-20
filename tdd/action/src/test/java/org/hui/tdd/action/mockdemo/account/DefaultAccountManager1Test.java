package org.hui.tdd.action.mockdemo.account;

import org.hui.tdd.action.mockdemo.configurations.MockConfiguration;
import org.junit.Test;

/**
 * @author Hui.Liu
 * @since 2021-12-05 16:43
 */
public class DefaultAccountManager1Test {
    @Test
    public void testFindAccountByUser() {
        MockLog logger = new MockLog();
        MockConfiguration configuration = new MockConfiguration();
        configuration.setSQL("SELECT * [...]");
        DefaultAccountManager1 am = new DefaultAccountManager1(logger, configuration);

        Account account = am.findAccountForUser("1234");

        // Perform asserts here
    }
}
