package org.hui.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Slf4j
public class SelectorClient {
    private Selector selector;

    private ByteBuffer outBuff = ByteBuffer.allocate(1024);
    private ByteBuffer inBuff = ByteBuffer.allocate(1024);

    private int keys = 0;
    private SocketChannel channel = null;

    public void initClient() throws IOException {
        channel = SocketChannel.open();
        selector = Selector.open();
        channel.configureBlocking(false);

        channel.connect(new InetSocketAddress("127.0.0.1", 8888));
        channel.register(this.selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException {
        while (true) {
            keys = this.selector.select();
            if (keys > 0) {
                Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                            log.info("完成连接");
                        }
                        channel.register(this.selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        outBuff.clear();
                        log.info("客户端正在写数据");
                        channel.write(ByteBuffer.wrap("我是 client".getBytes(StandardCharsets.UTF_8)));
                        channel.register(this.selector, SelectionKey.OP_READ);
                        log.info("客户端写数据完成");
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        inBuff.clear();
                        log.info("客户端开始读取数据");
                        channel.read(inBuff);
                        log.info("读取到数据： {}", new String(inBuff.array(), StandardCharsets.UTF_8));
                        log.info("客户端完成读取数据");
                    }
                }
            }
        }
    }

    public void start() {
        try {
            initClient();
            listen();
        } catch (IOException e) {
            log.info("clinet io exception.", e);
        }
    }

    public static void main(String[] args) {
        new SelectorClient().start();
    }
}
