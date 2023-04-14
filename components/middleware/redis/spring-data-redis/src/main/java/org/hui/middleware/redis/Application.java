package org.hui.middleware.redis;

import org.hui.middleware.redis.config.JedisConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hui.Liu
 * @since 2022-03-02 16:51
 */
public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JedisConfig.xml");
        System.out.println(context.getBean(JedisConfig.class));
    }
}
