package org.hui.middleware.springframework.core.xml;

import org.hui.middleware.springframework.core.xml.services.PetStoreServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @author Hui.Liu
 * @since 2022-03-04 11:17
 */
public class ApplicationStartTest {

    @Test
    public void testGetContextByClassPathXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        assertNotNull(context);
    }

    @Test
    public void testGetPetStorBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        PetStoreServiceImpl petStoreService = context.getBean(PetStoreServiceImpl.class);
        assertNotNull(petStoreService);
        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
    }
}
