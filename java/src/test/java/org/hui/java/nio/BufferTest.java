package org.hui.java.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.IntBuffer;

@Slf4j
public class BufferTest {
    @Test
    public void testAllocate() {

        IntBuffer buff1 = IntBuffer.allocate(10);
//        buff1.mark();
        buff1.put(1);
        buff1.put(2);
        buff1.put(3);
//        buff1.reset();
        buff1.flip();
        for (int i = 0;i<buff1.limit();i++) {
            System.out.println(buff1.get());
        }
    }
    @Test public void testWrap() {
        int[] arr = new int[]{3, 5, 1};

        IntBuffer buff = IntBuffer.wrap(arr);
        arr[1] = 10;

        for (int i = 0;i<3;i++) {
            System.out.println(buff.get());
        }
        IntBuffer buff3 = IntBuffer.wrap(arr, 0, 2);

        IntBuffer copBuff = buff.duplicate();
    }
}
