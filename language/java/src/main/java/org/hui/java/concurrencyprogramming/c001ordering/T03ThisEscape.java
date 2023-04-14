package org.hui.java.concurrencyprogramming.c001ordering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class T03ThisEscape {
    private static final Logger LOGGER = LoggerFactory.getLogger(T03ThisEscape.class);
    private int num = 8;

    public T03ThisEscape() {
        new Thread(() -> {
            LOGGER.info(String.valueOf(this.num));
        }).start();
    }

    public static void main(String[] args) throws IOException {
        new T03ThisEscape();
        System.in.read();
    }
}
