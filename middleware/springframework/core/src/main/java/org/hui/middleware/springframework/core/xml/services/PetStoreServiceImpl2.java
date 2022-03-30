package org.hui.middleware.springframework.core.xml.services;

import org.hui.middleware.springframework.core.xml.dao.jpa.JpaAccountDao;
import org.hui.middleware.springframework.core.xml.dao.jpa.JpaItemDao;

/**
 * @author Hui.Liu
 * @since 2022-03-04 11:22
 */
public class PetStoreServiceImpl2 {
    private final JpaAccountDao accountDao;
    private final JpaItemDao itemDao;
    private final String str;

    public PetStoreServiceImpl2(JpaAccountDao accountDao, JpaItemDao itemDao, String str) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.str = str;
    }

}
