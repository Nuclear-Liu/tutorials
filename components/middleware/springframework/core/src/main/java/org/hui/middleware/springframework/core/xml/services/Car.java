package org.hui.middleware.springframework.core.xml.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author Hui.Liu
 * @since 2022-04-01 16:49
 */
@Slf4j
public class Car implements BeanNameAware {
    public String getBeanName() {
        return beanName;
    }

    private String beanName;

    @Override
    public void setBeanName(String name) {
        log.info("set Car bean name: {}", name);
        this.beanName = name;
    }
}
