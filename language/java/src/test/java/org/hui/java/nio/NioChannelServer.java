package org.hui.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class NioChannelServer {
    private ByteBuffer buff = ByteBuffer.allocate(1024);

    // create view
    private IntBuffer intBuff = buff.asIntBuffer();

    private SocketChannel clientChannel = null;

    private ServerSocketChannel serverChannel = null;

    public void openChannel() throws IOException {
        // create channel
        serverChannel = ServerSocketChannel.open();
        // bind port.
        serverChannel.socket().bind(new InetSocketAddress(8888));
        log.info("服务器通道已经打开");
    }

    public void waitReqCon() throws IOException {
        while (true) {
            clientChannel = serverChannel.accept();
            if (null != clientChannel) {
                log.info("新的连接加入");
            }
            processReq(); // process req
            clientChannel.close();
        }
    }

    public void processReq() throws IOException {
        log.info("开始读取和处理客户端数据");
        buff.clear();
        clientChannel.read(buff);
        int result = intBuff.get(0) + intBuff.get(1);
        buff.flip();
        buff.clear();
        // modify view
        intBuff.put(0, result);
        clientChannel.write(buff);
        log.info("读取和处理客户端数据完成");
    }

    public void start() {
        try {
            // open server channel
            openChannel();

            waitReqCon();

            clientChannel.close();
            log.info("server process end.");
        } catch (IOException e) {
            log.info("server io exception.", e);
        }
    }

    public static void main(String[] args) {
        new NioChannelServer().start();
    }
}
