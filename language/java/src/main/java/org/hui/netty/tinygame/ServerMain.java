package org.hui.netty.tinygame;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.hui.netty.tinygame.cmdhandler.CmdHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ServerMain {
    public static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    /**
     * main function
     */
    public static void main(String[] args) {
        CmdHandlerFactory.init();
        GameMsgRecognizer.init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(12345))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new HttpServerCodec(), // Http server decode.
                                new HttpObjectAggregator(65535), // Http context length.
                                new WebSocketServerProtocolHandler("/broadcast"), // WebSocket Protocol processor.
                                new GameMsgDecoder(), // 自定义解码器
                                new GameMsgEncoder(), // 自定义编码器
                                new GameMsgHandler() // Game Message Protocol processor.
                        );
                    }
                });

        try {
            ChannelFuture future = bootstrap.bind().sync();
            if (future.isSuccess()) {
                LOGGER.info("server start.");
            } else {
                LOGGER.error("server not start.");
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException se) {
            LOGGER.error("server start exception.", se);
        } finally {
            try {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException ce) {
                LOGGER.error("server shutdown error.", ce);
            }
        }
    }
}
