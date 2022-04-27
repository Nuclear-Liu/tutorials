package org.hui.java.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T004ParentAndChild {
    public static void main(String[] args) {
        log.info("{}", T004ParentAndChild.class.getClassLoader());
        log.info("{}", T004ParentAndChild.class.getClassLoader().getClass().getClassLoader());
        log.info("{}", T004ParentAndChild.class.getClassLoader().getParent());
        log.info("{}", T004ParentAndChild.class.getClassLoader().getParent().getParent());
    }
}
