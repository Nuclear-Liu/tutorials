package org.hui.java.jvm.classloader;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.jvm.Hello;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class T006MSBClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File("d:/test/", name.replace(".", "/").concat(".class"));

        try {
            FileInputStream fileInputStream = new FileInputStream(f);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int b = 0;
            while ((b = fileInputStream.read()) != 0) {
                byteArrayOutputStream.write(b);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();
            fileInputStream.close();

            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        T006MSBClassLoader loader = new T006MSBClassLoader();
        Class<?> clazz = loader.loadClass("org.hui.java.jvm.Hello");

        Hello hello = (Hello)clazz.newInstance();
        hello.m();

        log.info("{}", loader.getClass().getClassLoader());
        log.info("{}", loader.getParent());
    }
}
