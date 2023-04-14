package org.hui.stomp.client.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ChatStompSessionHandler extends StompSessionHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatStompSessionHandler.class);

//    private final ChatWebSocketClient chatWebSocketClient;
//
//    public ChatStompSessionHandler(ChatWebSocketClient chatWebSocketClient) {
//        this.chatWebSocketClient = chatWebSocketClient;
//    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        LOGGER.info("接收消息：", (String) payload);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        LOGGER.error("", exception);
        try {
            TimeUnit.SECONDS.sleep(3);
//            chatWebSocketClient.connect();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
