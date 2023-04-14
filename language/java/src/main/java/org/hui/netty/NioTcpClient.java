package org.hui.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class NioTcpClient {

    public static final Logger LOGGER = LoggerFactory.getLogger(NioTcpClient.class);

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        LOGGER.info("Received data");
                        ctx.writeAndFlush(Unpooled.copiedBuffer("hello",StandardCharsets.UTF_8));
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
        // ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("Connection established");
                    // future.channel().writeAndFlush(Unpooled.copiedBuffer("hello", StandardCharsets.UTF_8));
                } else {
                    LOGGER.error("Connection attempt failed", future.cause().getStackTrace());
                }
                future.channel().closeFuture();
                group.shutdownGracefully();
            }
        });

    }
}
