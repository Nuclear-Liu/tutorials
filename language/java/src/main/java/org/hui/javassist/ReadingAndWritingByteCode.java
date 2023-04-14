package org.hui.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

public class ReadingAndWritingByteCode {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReadingAndWritingByteCode.class);

    public  static void main(String[] args) throws NotFoundException, IOException, CannotCompileException {
        // testClassPoolGet();
        testMakeClass();
    }

    private static void testClassPoolGet() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("test.Rectangle");
        cc.setSuperclass(pool.get("test.Point"));
        cc.writeFile();
    }

    public static void testMakeClass() throws CannotCompileException, NotFoundException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Point");

        Class<?> aClass = cc.toClass();
    }
}
