package org.hui.middleware.springframework.core.xml;

import org.hui.middleware.springframework.core.xml.services.PetStoreServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @author Hui.Liu
 * @since 2022-03-04 11:17
 */
public class ApplicationContextTest {

    @Test
    public void testByClassPathXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        assertNotNull(context);
    }

    @Test
    public void testByGeneric() {
        GenericApplicationContext context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("application.xml");
        context.refresh();
        PetStoreServiceImpl petStoreService = context.getBean(PetStoreServiceImpl.class);
        assertNotNull(petStoreService);
    }

}
