package org.hui.java.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T001ClassLoadingProcedure {
    public static void main(String[] args) {
        log.info("T.count: {}",T.count);
    }
}

@Slf4j
class T {
    public static T t = new T();
    public static int count = 2;

    private T() {
        count++;
        log.info("count: {}",T.count);
    }
}
