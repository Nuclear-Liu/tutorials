package org.hui.netty.plain;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class NettyNioServer {
    public static final Logger LOGGER = LoggerFactory.getLogger(NettyNioServer.class);

    public void server(int port) throws InterruptedException {
        final ByteBuf buf = Unpooled.copiedBuffer("Hi!\r\n", StandardCharsets.UTF_8);
        // 为非阻塞模式使用 NioEventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 指定 ChannelInitializer 对于每个已接受的连接都调用它
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    // 添加 ChannelInboundHandlerAdapter 以接收和处理事件
                                    new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            // 将消息写到客户端，并添加 ChannelFutureListener 以便消息一被写入就关闭连接
                                            ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                        }
                                    }
                            );
                        }
                    });
            // 绑定服务器以接受连接
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            // 释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
