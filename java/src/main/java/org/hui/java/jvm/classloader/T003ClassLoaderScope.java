package org.hui.java.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T003ClassLoaderScope {
    public static void main(String[] args) {
        String pathBoot = System.getProperty("java.class.path");
        System.out.println(pathBoot.replace(";", System.lineSeparator()));
        System.out.println("------------------------");
        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println(pathExt.replace(";", System.lineSeparator()));

    }
}
