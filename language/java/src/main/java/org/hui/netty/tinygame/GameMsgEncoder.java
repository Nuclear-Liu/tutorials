package org.hui.netty.tinygame;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameMsgEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (null == msg || !(msg instanceof GeneratedMessageV3)) {
            super.write(ctx, msg, promise);
        }

        short msgCode = GameMsgRecognizer.getMsgCode(msg.getClass());

        if (msgCode <= -1) {
            LOGGER.error("msg: {} can not get msgCode: {}", msg.getClass().getName(), msgCode);
            return;
        }

        byte[] bytes = ((GeneratedMessageV3) msg).toByteArray();

        ByteBuf buf = ctx.alloc().buffer();
        buf.writeShort((short) 0);
        buf.writeShort(msgCode);
        buf.writeBytes(bytes);

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buf);
        super.write(ctx, frame, promise);
    }
}
