package org.hui.middleware.springframework.core.xml;

import lombok.extern.slf4j.Slf4j;
import org.hui.middleware.springframework.core.xml.services.PetStoreServiceImpl;
import org.hui.middleware.springframework.core.xml.services.PetStoreServiceImpl2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @author Hui.Liu
 * @since 2022-03-04 15:41
 */
@Slf4j
public class BeanTest {

    @Test
    public void testGetPetStore() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        PetStoreServiceImpl petStoreService = context.getBean(PetStoreServiceImpl.class);
        assertNotNull(petStoreService);
        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
    }
    @Test
    public void testAware() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        PetStoreServiceImpl2 petStoreServiceImpl2 = context.getBean(PetStoreServiceImpl2.class);
        System.out.println(petStoreServiceImpl2.getApplicationContex());
        log.info(petStoreServiceImpl2.getStr());
    }
    @Test public void testGetBeanName() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        PetStoreServiceImpl2 petStoreServiceImpl2 = context.getBean(PetStoreServiceImpl2.class);
        log.info(petStoreServiceImpl2.getBeanName());
    }

}
