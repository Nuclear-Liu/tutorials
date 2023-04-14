package org.hui.tdd.action.mockdemo.account;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hui.Liu
 * @since 2021-12-05 1:27
 */
public class MockAccountManager implements AccountManager {

    private Map<String, Account> accounts = new HashMap<>();

    public void addAccount(String userId, Account account) {
        this.accounts.put(userId, account);
    }

    @Override
    public Account findAccountForUser(String userId) {
        return this.accounts.get(userId);
    }

    @Override
    public void updateAccount(Account account) {
        // do nothing
    }
}
