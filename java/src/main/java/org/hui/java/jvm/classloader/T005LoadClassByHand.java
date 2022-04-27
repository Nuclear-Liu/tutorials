package org.hui.java.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T005LoadClassByHand {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = T005LoadClassByHand.class.getClassLoader().loadClass("org.hui.java.jvm.classloader.T002ClassLoaderLevel");
        log.info("class name: {}", clazz.getName());
    }
}
