package org.hui.middleware.springframework.core.xml.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hui.middleware.springframework.core.xml.dao.jpa.JpaAccountDao;
import org.hui.middleware.springframework.core.xml.dao.jpa.JpaItemDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Hui.Liu
 * @since 2022-03-04 11:22
 */
@Slf4j
public class PetStoreServiceImpl2 implements ApplicationContextAware, BeanNameAware {
    private final JpaAccountDao accountDao;
    private final JpaItemDao itemDao;
    private final String str;
    private ApplicationContext applicationContex;
    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    public PetStoreServiceImpl2(JpaAccountDao accountDao, JpaItemDao itemDao, String str) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.str = str;
    }

    public ApplicationContext getApplicationContex() {
        return applicationContex;
    }

    public String getStr() {
        return str;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("set ApplicationContext.");
        this.applicationContex = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        log.info("set bean name: {}", name);
        this.beanName = name;
    }
}
