package org.hui.middleware.springframework.core.xml.services;

import org.hui.middleware.springframework.core.xml.dao.jpa.JpaAccountDao;
import org.hui.middleware.springframework.core.xml.dao.jpa.JpaItemDao;

/**
 * @author Hui.Liu
 * @since 2022-03-04 11:22
 */
public class PetStoreServiceImpl {
    private JpaAccountDao accountDao;
    private JpaItemDao itemDao;

    public JpaAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(JpaAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public JpaItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(JpaItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
