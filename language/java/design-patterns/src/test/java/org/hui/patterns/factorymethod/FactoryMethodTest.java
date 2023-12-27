package org.hui.patterns.factorymethod;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FactoryMethodTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void whenGetSimpleBean_thenReturnConstructedBean() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Foo foo = context.getBean(Foo.class);

        assertNotNull(foo);

        Foo foo1 = applicationContext.getBean(Foo.class);
        assertNotNull(foo1);
    }

    @Test
    public void whenGetPrototypeBean_thenReturnConstructedBean() {
        String expectedName = "littleHui";
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Bar bar = context.getBean(Bar.class, expectedName);
        assertNotNull(bar);
        assertEquals(bar.getName(), expectedName);

        Bar bar1 = applicationContext.getBean(Bar.class, expectedName);
        assertNotNull(bar1);
        assertEquals(bar1.getName(), expectedName);

    }
}