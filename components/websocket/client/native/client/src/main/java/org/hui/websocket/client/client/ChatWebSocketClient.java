package org.hui.websocket.client.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class ChatWebSocketClient extends WebSocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatWebSocketClient.class);

    public ChatWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.info("client connected.");
    }

    @Override
    public void onMessage(String s) {
        LOGGER.info("client receive message: {}", s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        LOGGER.info("client closed.");
    }

    @Override
    public void onError(Exception e) {
        LOGGER.error("client error.",e);
    }
}
