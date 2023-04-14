package org.hui.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteBufTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ByteBufTest.class);

    @Test
    public void heapBufTest() {
        ByteBuf heapBuf = Unpooled.wrappedBuffer("Hello".getBytes(StandardCharsets.UTF_8));

        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }

        heapBuf.readerIndex(0);
        heapBuf.writerIndex(0);
        heapBuf.setBytes(0,"World".getBytes(StandardCharsets.UTF_8));

        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }
    }

    public static void handleArray(byte[] array, int offset, int length) {
        int curr = offset;
        byte[] context = Arrays.copyOfRange(array, offset, offset + length);
        System.out.println(new String(context, StandardCharsets.UTF_8));
    }
}
