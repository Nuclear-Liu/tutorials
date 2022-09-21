package org.hui.netty.plain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PlainOioServer {
    public static final Logger LOGGER = LoggerFactory.getLogger(PlainOioServer.class);

    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);
        try {
            for (; ; ) {
                final Socket clientSocket = socket.accept();
                LOGGER.info("Accepted connection from {}", clientSocket);
                new Thread(() -> {
                    OutputStream out;
                    try {
                        out= clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        LOGGER.error("IOExecution: ",  e);
                    }
                }).start();
            }
        } catch (IOException e) {
            LOGGER.error("IOException.",e);
        } finally {
            socket.close();
        }
    }
}
