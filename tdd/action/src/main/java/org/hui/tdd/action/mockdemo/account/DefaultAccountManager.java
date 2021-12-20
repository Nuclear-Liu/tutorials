package org.hui.tdd.action.mockdemo.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Hui.Liu
 * @since 2021-12-05 15:25
 */
public class DefaultAccountManager implements AccountManager {

    /**
     * Logger instance
     */
    private static final Log LOGGER = LogFactory.getLog(DefaultAccountManager.class);

    /**
     * Finds an account for user with the given userID.
     * @param userId
     * @return
     */
    @Override
    public Account findAccountForUser(String userId) {
        LOGGER.debug("Getting account for user [" + userId + "]");
        ResourceBundle bundle = PropertyResourceBundle.getBundle("techical");
        String sql = bundle.getString("FIND_ACCOUNT_FOR_USER");

        // Some code logic to load a user account using JDBC
        return null;
    }

    /**
     * Update the given account
     * @param account
     */
    @Override
    public void updateAccount(Account account) {
        // Perform database access here
    }
}
