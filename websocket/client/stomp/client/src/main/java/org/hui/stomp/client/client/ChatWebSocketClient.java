package org.hui.stomp.client.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ChatWebSocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatWebSocketClient.class);

    @Value("${websocket.endpoint}")
    private String endpoint;

    private static StompSession stompSession;

    private final ChatStompSessionHandler chatStompSessionHandler;

    public ChatWebSocketClient(ChatStompSessionHandler chatStompSessionHandler) {
        this.chatStompSessionHandler = chatStompSessionHandler;
    }

    public StompSession getStompSession() {
        return stompSession;
    }

    String userId = "100";

    public void connect() {
        if (Objects.isNull(stompSession) || !stompSession.isConnected()) {
            LOGGER.info("connecting.");
            List<Transport> transports = new ArrayList<>();
            transports.add(new WebSocketTransport(new StandardWebSocketClient()));
            SockJsClient sockJsClient = new SockJsClient(transports);
            WebSocketStompClient webSocketStompClient = new WebSocketStompClient(sockJsClient);
            webSocketStompClient.setMessageConverter(new StringMessageConverter());
            webSocketStompClient.setDefaultHeartbeat(new long[]{20000, 0});
            ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.afterPropertiesSet();
            webSocketStompClient.setTaskScheduler(taskScheduler);
            StompHeaders stompHeaders = new StompHeaders();
            stompHeaders.add("token", "token");
            WebSocketHttpHeaders webSocketHttpHeaders = null;

            try {
                ListenableFuture<StompSession> future = webSocketStompClient.connect(endpoint, webSocketHttpHeaders,
                        stompHeaders, chatStompSessionHandler);
                stompSession = future.get();
                stompSession.setAutoReceipt(true);
                stompSession.subscribe("/queue/" + userId + "/topic", chatStompSessionHandler);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

        }
    }

    public String sendMessage(String msg) {
        stompSession.send("/app/sendToUser", msg.getBytes());
        return "succeed";
    }

}
