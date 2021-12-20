package org.hui.tdd.action.mockdemo.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hui.tdd.action.mockdemo.configurations.Configuration;
import org.hui.tdd.action.mockdemo.configurations.DefaultConfiguration;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Hui.Liu
 * @since 2021-12-05 15:25
 */
public class DefaultAccountManager1 implements AccountManager {

    /**
     * Logger instance.
     */
    private Log logger;

    /**
     * Configuration to use.
     */
    private Configuration configuration;

    /**
     * Constructor with no parameters
     */
    public DefaultAccountManager1() {
        this(LogFactory.getLog(DefaultAccountManager1.class), new DefaultConfiguration("technical"));
    }

    /**
     * Constructor with logger and configuration parmeters.
     * @param logger
     * @param configuration
     */
    public DefaultAccountManager1(Log logger, Configuration configuration) {
        this.logger = logger;
        this.configuration = configuration;
    }

    /**
     * Finds an account for user with the given userID.
     * @param userId
     * @return
     */
    @Override
    public Account findAccountForUser(String userId) {
        this.logger.debug("Getting account for user [" + userId + "]");
        this.configuration.getSQL("FIND_ACCOUNT_FOR_USER");

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
