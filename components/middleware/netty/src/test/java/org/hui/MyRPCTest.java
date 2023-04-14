package org.hui;

import ch.qos.logback.core.net.server.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 1. 实现一个 RPC: 如同调用本地方法一样调用远程方法（java 面向接口开发，依靠动态代理）
 * 2. 来回通信，连接数量，拆包 粘包
 * 3. 动态代理、序列化、协议封装
 * 4. 连接池
 */
public class MyRPCTest {

    // consumer 端
    @Test public void get() {

        Car car = proxyGet(Car.class); // 动态代理

        car.ooxx("hello");

        Fly fly = proxyGet(Fly.class);

        fly.xxoo("hello");

    }

    public static <T>T proxyGet(Class<T> interfaceInfo) {
        // 可以使用多种方式实现动态代理

        ClassLoader loader = interfaceInfo.getClassLoader();

        Class<?>[] methodInfo = {interfaceInfo};

        return (T) Proxy.newProxyInstance(loader, methodInfo, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // consumer 调用 provider 的过程
                // 1.  调用的服务，方法，参数 --》 封装为 message
                String name = interfaceInfo.getName(); // 服务
                String methodName = method.getName(); // 方法
                Class<?>[] parameterTypes = method.getParameterTypes(); // 参数类型

                MyContent content = new MyContent();
                content.setName(name);
                content.setMethodName(methodName);
                content.setParameterTypes(parameterTypes);
                content.setArgs(args);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream oout = new ObjectOutputStream(out);
                oout.writeObject(content);
                byte[] msgBody = out.toByteArray();

                // 2. requestID（确定请求身份） + message
                // 协议： head(uuid, bodysize) body
                MyHeader header = createHeader(msgBody);
                out.reset();
                oout = new ObjectOutputStream(out);
                oout.writeObject(header);
                byte[] msgHeader = out.toByteArray();

                // 3. 连接池（获取连接）
                ClientFactory factory = ClientFactory.getFactory();
                NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("127.0.0.1", 9090));

                // 获取连接过程：开始-创建 过程-直接取

                // 发送
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
                byteBuf.writeBytes(msgHeader);
                byteBuf.writeBytes(msgBody);
                ChannelFuture channelFuture = clientChannel.writeAndFlush(msgBody);
                channelFuture.sync();

                CountDownLatch countDownLatch = new CountDownLatch(1);
                clientChannel.wait();
                // 4. 异步请求，返回信息后怎么样继续执行：如何实现 睡眠、回调，如何让线程停止，并且可以继续执行；（countDownLunch



                return null;
            }
        });
    }

    public static MyHeader createHeader(byte[] msg) {
        MyHeader header = new MyHeader();
        int size = msg.length;
        int f = 0x14141414;
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        header.setFlag(f);
        header.setDataLen(size);
        header.setRequestID(requestId);
        return header;
    }

}


class ClientFactory {

    int poolSize = 1;

    Random random = new Random();
    NioEventLoopGroup clientWorker;

    private static final ClientFactory factory;

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    // 一个 consumer 可以连接多个 provider 每个 provider 有自己的 pool （k,v)

    ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();

    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool clientPool = outboxs.get(address);
        if (clientPool == null) {
            outboxs.putIfAbsent(address, new ClientPool(poolSize));
            clientPool = outboxs.get(address);
        }

        int i = random.nextInt(poolSize);
        if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
            return clientPool.clients[i];
        }
        synchronized (clientPool.lock[i]) {
            return clientPool.clients[i] = create(address);
        }
    }

    private NioSocketChannel create(InetSocketAddress address) {
        // netty 客户端
        clientWorker = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ClientResponses());
                    }
                }).connect(address);
        NioSocketChannel channel = null;
        try {
            channel = (NioSocketChannel) connect.sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            return channel;
        }
    }

}

class ClientResponses extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        if (buf.readableBytes() >= 160) {
            byte[] bytes = new byte[160];
            buf.readBytes(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            MyHeader header = (MyHeader) oin.readObject();
            System.out.println(header);
            if (buf.readableBytes() >= header.getDataLen()) {
                byte[] data = new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                MyContent content = (MyContent) doin.readObject();
                System.out.println(content);
            }
        }
    }
}

class ClientPool {
    NioSocketChannel[] clients;
    Object[] lock;

    ClientPool(int size) {
        clients = new NioSocketChannel[size];
        lock = new Object[size];
//        for (int i = 0; i < clients.length; i++) {
//            clients[i] = new NioSocketChannel();
//        }
        for (int i = 0; i < lock.length; i++) {
            lock[i] = new Object();
        }
    }
}

// 协议
@Setter
@Getter
@ToString
class MyHeader implements Serializable {
    // 协议标识
    // UUID:requestID
    // DataLen
    int flag; // 32 bite
    long requestID;
    long dataLen;
}

@Setter
@Getter
@ToString
class MyContent implements Serializable {
    String name;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] args;
}

interface Car {
    void ooxx(String msg);
}

interface Fly {
    void xxoo(String msg);
}
