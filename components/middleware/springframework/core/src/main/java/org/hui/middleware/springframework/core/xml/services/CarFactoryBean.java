package org.hui.middleware.springframework.core.xml.services;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author
 * @since 2022-04-01 16:48
 */
public class CarFactoryBean implements FactoryBean<Car> {
    @Override
    public Car getObject() throws Exception {
        return new Car();
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
