package org.hui.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServerHandler.class);

    /**
     * 将接收到的消息写给发送者，而不冲刷出战消息.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        LOGGER.info("server received: {}", in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    /**
     * 将未决消息(指暂存于 ChannelOutboundBuffer 中的消息，在下次调用 flush() 或者 writeAndFlush() 方法时将会尝试写出到套接字)
     * 冲刷到远程节点
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /* 打印异常栈信息 */
        cause.printStackTrace();
        /* 关闭该 Channel */
        ctx.close();
    }
}
