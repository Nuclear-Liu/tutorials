package org.hui.netty.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable // 标记该类的实例可以被多个 Channel 共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final Logger LOGGER = LoggerFactory.getLogger(EchoClientHandler.class);

    /**
     * 当被通知 Channel 是活跃的时候，发送一条消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * 记录已接收消息的转储
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        LOGGER.info("Client received: {}", msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在发生异常时，记录错误并关闭 Channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
