package org.hui.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class NioChannelClient {
    private SocketChannel channel = null;

    private ByteBuffer buff = ByteBuffer.allocate(8);
    private IntBuffer intBuff = buff.asIntBuffer();

    public SocketChannel connect() throws IOException {
        return SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
    }

    public void sendRequest(int a, int b) throws IOException {
        buff.clear();
        intBuff.put(0, a);
        intBuff.put(1, b);
        channel.write(buff);
        log.info("发送加法请求： {} + {} ", a, b);
    }

    public int receiveResult() throws IOException {
        buff.clear();
        channel.read(buff);
        return intBuff.get(0);
    }

    public int getSum(int a, int b) {
        int result = 0;
        try {
            channel = connect();

            sendRequest(a, b);

            result = receiveResult();
        } catch (IOException e) {
            log.info("client connection IOException.", e);
        } finally {
            return result;
        }
    }

    public static void main(String[] args) {
        int result = new NioChannelClient().getSum(56, 34);
        log.info("加法运算的最后结果为： {}", result);
    }
}
