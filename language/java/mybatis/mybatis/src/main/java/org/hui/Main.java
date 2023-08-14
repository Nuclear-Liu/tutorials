package org.hui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        System.out.println("Hello world!");
        LOGGER.info("hello world");
    }
}