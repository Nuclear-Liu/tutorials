package org.hui;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class nettyByteBuffTest {
    @Test
    public void testByteBuf() {
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);
        // 非池化
//        ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        // 池化创建
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        print(buf);

        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
//        buf.writeBytes(new byte[]{1, 2, 3, 4});
//        print(buf);
    }

    public static void print(ByteBuf buf) {
        System.out.println("ByteBuf:: isReadable()      :" + buf.isReadable());
        System.out.println("ByteBuf:: readerIndex()     :" + buf.readerIndex()); // 开始读取的位置索引
        System.out.println("ByteBuf:: readableBytes()   :" + buf.readableBytes());
        System.out.println("ByteBuf:: isWritable()      :" + buf.isWritable());
        System.out.println("ByteBuf:: writerIndex()     :" + buf.writerIndex());
        System.out.println("ByteBuf:: writableBytes()   :" + buf.writableBytes());
        System.out.println("ByteBuf:: capacity()        :" + buf.capacity());
        System.out.println("ByteBuf:: maxCapacity()     :" + buf.maxCapacity());
        System.out.println("ByteBuf:: isDirect()        :" + buf.isDirect());
        System.out.println("--------------------------------------------------");
    }

    /**
     * nc -l 172.28.21.81 9090 启动一个服务器.
     * <p>
     * 1. 主动发送数据
     * 2. 基于事件接收数据： selector
     */
    @Test
    public void testLoopExecutor() throws IOException {
        // Group 线程池
        // NioEventLoopGroup 不是 selector
        NioEventLoopGroup selector = new NioEventLoopGroup(2);
        selector.execute(() -> {
            for (; ; ) {
                System.out.println("Hello world 001.");
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        selector.execute(() -> {
            for (; ; ) {
                System.out.println("Hello world 002.");
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        System.in.read();
    }

    @Test
    public void testClientMode() throws InterruptedException {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);

        // client mode
        NioSocketChannel client = new NioSocketChannel();

        thread.register(client);

        // 写事件放在 pipeline 中
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler()); // 写事件

        // reactor 异步执行
        ChannelFuture connect = client.connect(new InetSocketAddress("172.28.21.81", 9090));
        ChannelFuture sync = connect.sync();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        sync.channel().closeFuture().sync();

        System.out.println("client over....");
    }

    @Test
    public void testServerMode() throws InterruptedException {

        NioEventLoopGroup thread = new NioEventLoopGroup(1);

        NioServerSocketChannel server = new NioServerSocketChannel();

        thread.register(server);

        // 连接不确定什么时候  基于事件回调
        ChannelPipeline p = server.pipeline();
//        p.addLast(new MyAcceptHandler(thread, new MyInHandler())); // 接收handler 注册到selector
        p.addLast(new MyAcceptHandler(thread, new ChannelInit())); // 接收handler 注册到selector

        ChannelFuture bind = server.bind(new InetSocketAddress(9090));
        bind.sync().channel().closeFuture().sync();


    }

    @Test public void testNettyClient() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(group)
                .channel(NioSocketChannel.class)
//                .handler(new ChannelInit())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .connect(new InetSocketAddress("172.28.21.81", 9090));

        Channel client = connect.sync().channel();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        client.closeFuture().sync();
    }

    @Test public void nettyServer() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
//                .childHandler(new ChannelInit())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .bind(new InetSocketAddress(9090));

        bind.sync().channel().closeFuture().sync();

    }
}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler inHandler) {
        this.selector = thread;
        this.handler = inHandler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server registered...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel client = (SocketChannel) msg; // netty 处理 accept 调用

        // 响应 handler
        ChannelPipeline p = client.pipeline();
        p.addLast(handler);

        // 注册
        selector.register(client);
    }
}

@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler());
        p.remove(this);
    }
}

/**
 * 用户自定义实现
 *
 * 一般情况下 不是应该是 @ChannelHandler.Sharable
 */
// @ChannelHandler.Sharable
class MyInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client registered....");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // read 移动了指针
        // CharSequence str = buf.readCharSequence(buf.readableBytes(), CharsetUtil.UTF_8);
        CharSequence str = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);

        ctx.writeAndFlush(buf);
    }
}
