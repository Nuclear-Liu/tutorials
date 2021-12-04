package org.hui.tdd.action.mockdemo.account;

/**
 * @author Hui.Liu
 * @since 2021-12-05 1:18
 */
public interface AccountManager {
    Account findAccountForUser(String userId);
    void updateAccount(Account account);
}
