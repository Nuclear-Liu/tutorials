package org.hui.netty.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class EchoServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);

    private final int prot;

    public EchoServer(int prot) {
        this.prot = prot;
    }

    public void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();

        EventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(prot))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });
    }
}
