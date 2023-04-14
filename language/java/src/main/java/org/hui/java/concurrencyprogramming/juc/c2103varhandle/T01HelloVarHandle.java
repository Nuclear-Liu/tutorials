package org.hui.java.concurrencyprogramming.juc.c2103varhandle;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

@Slf4j
public class T01HelloVarHandle {
    int x = 8;
    private static VarHandle handle;

    static {
        try {
            handle = MethodHandles.lookup().findVarHandle(T01HelloVarHandle.class, "x", int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        T01HelloVarHandle t = new T01HelloVarHandle();

        log.info("{}", handle.get(t));
        handle.set(t, 9);
        log.info("{}", t.x);

        handle.compareAndSet(t, 9, 10);
        log.info("{}", t.x);

        handle.getAndAdd(t, 10);
        log.info("{}", t.x);

        SoftReference<byte[]> m = new SoftReference<>(new byte[1024]);
        byte[] bytes = m.get();

        PhantomReference<String> phantomReference = new PhantomReference<>(new String("sdfwe"), new ReferenceQueue<String>());
    }

}
