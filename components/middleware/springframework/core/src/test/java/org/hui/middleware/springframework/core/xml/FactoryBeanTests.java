package org.hui.middleware.springframework.core.xml;

import lombok.extern.slf4j.Slf4j;
import org.hui.middleware.springframework.core.xml.services.Car;
import org.hui.middleware.springframework.core.xml.services.CarFactoryBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hui.Liu
 * @since 2022-04-01 16:53
 */
@Slf4j
public class FactoryBeanTests {
    @Test public void testCarBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testFactory.xml");
        Car car = (Car) context.getBean("carFactoryBean");
        Assert.assertNotNull(car);
        log.info(car.getBeanName());
    }
    @Test public void testCarFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testFactory.xml");
        CarFactoryBean carFactoryBean = (CarFactoryBean) context.getBean("&carFactoryBean");
        Assert.assertNotNull(carFactoryBean);
    }
}
