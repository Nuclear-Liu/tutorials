package org.hui.middleware.springframework;

import org.hui.middleware.springframework.service.Hello;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hui.Liu
 * @since 2022-03-02 15:14
 */
public class XmlApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        System.out.println(context.getBean(Hello.class));
    }
}
