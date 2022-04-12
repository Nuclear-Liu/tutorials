package org.hui.java.concurrencyprogramming.c001ordering;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class T03ThisEscape {
    private int num = 8;

    public T03ThisEscape() {
        new Thread(() -> {
            log.info(String.valueOf(this.num));
        }).start();
    }

    public static void main(String[] args) throws IOException {
        new T03ThisEscape();
        System.in.read();
    }
}
