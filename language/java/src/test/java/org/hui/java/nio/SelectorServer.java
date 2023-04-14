package org.hui.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Slf4j
public class SelectorServer {

    private Selector selector;
    private ServerSocketChannel serverChannel = null;
    private int keys = 0;

    public void initServer() throws IOException {
        this.selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        serverChannel.configureBlocking(false);

        SelectionKey key = serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        int len = channel.read(buff);
        String msg = "服务器接收到的消息：" + new String(buff.array(),0, len, StandardCharsets.UTF_8);
    }

    public void listen() throws IOException {
        log.info("服务器已经启动");
        while (true) {
            keys = this.selector.select();
            Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
            if (keys > 0) {
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel channel = serverChannel.accept();
                        channel.configureBlocking(false);

                        channel.write(ByteBuffer.wrap(new String("hello client").getBytes(StandardCharsets.UTF_8)));
                        channel.register(this.selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            } else {
                log.info("Select finished without any keys.");
            }
        }
    }

    public void start() {
        try {
            SelectorServer server = new SelectorServer();
            server.initServer();
            server.listen();
        } catch (IOException e) {
            log.info("server io exception.", e);
        }
    }

    public static void main(String[] args) {
        new SelectorServer().start();
    }
}
