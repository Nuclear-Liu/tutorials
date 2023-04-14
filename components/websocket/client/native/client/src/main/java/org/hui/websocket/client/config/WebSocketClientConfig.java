package org.hui.websocket.client.config;

import org.hui.websocket.client.client.ChatWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class WebSocketClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClientConfig.class);

    @Value("${websocket.endpoint}")
    private String endpoint;

    @Bean
    ChatWebSocketClient webSocketClient() {
        try {
            String userId = "100";
            ChatWebSocketClient webSocketClient = new ChatWebSocketClient(new URI(endpoint + userId));
            webSocketClient.connect();
            return webSocketClient;
        } catch (URISyntaxException e) {
            LOGGER.error("create websocket failed.", e);
        }
        return null;
    }
}
