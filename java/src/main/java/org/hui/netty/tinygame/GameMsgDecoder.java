package org.hui.netty.tinygame;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解码器
 */
public class GameMsgDecoder extends ChannelInboundHandlerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameMsgDecoder.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof BinaryWebSocketFrame)) {
            return;
        }

        // WebSocket 二进制消息会通过 HttpServerCodec 解码成 BinaryWebSocketFrame
        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf buf = frame.content();

        // 读取消息长度
        short msgLength = buf.readShort();
        LOGGER.info("msg length: {}", msgLength);
        // 读取消息编号
        short msgCode = buf.readShort();
        LOGGER.info("msg code: {}", msgCode);

        Message.Builder msgBuilder = GameMsgRecognizer.getBuilder(msgCode);
        if (null == msgBuilder) {
            LOGGER.error("msgCode: {} can not find", msgCode);
            return;
        }

        byte[] msgBody = new byte[buf.readableBytes()];
        buf.readBytes(msgBody);
        LOGGER.info("msg body: {}", ByteBufUtil.hexDump(msgBody));

        // 构建前清理
        msgBuilder.clear();
        msgBuilder.mergeFrom(msgBody);

        Message body = msgBuilder.build();
        if (null != body) {
            ctx.fireChannelRead(body);
        }
    }
}
