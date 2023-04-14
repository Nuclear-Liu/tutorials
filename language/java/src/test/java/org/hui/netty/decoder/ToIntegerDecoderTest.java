package org.hui.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToIntegerDecoderTest {

    @Test
    public void test() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(34);
        buf.writeInt(45);

        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder());
        assertTrue(channel.writeInbound(buf));
        assertTrue(channel.finish());

        assertEquals(34, (Integer) channel.readInbound());
        assertEquals(45, (Integer) channel.readInbound());

    }

    @Test
    public void test2() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(34);
        buf.writeInt(45);

        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder2());
        assertTrue(channel.writeInbound(buf));
        assertTrue(channel.finish());

        assertEquals(34, (Integer) channel.readInbound());
        assertEquals(45, (Integer) channel.readInbound());

    }
}