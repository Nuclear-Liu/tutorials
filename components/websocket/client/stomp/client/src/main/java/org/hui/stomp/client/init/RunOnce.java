package org.hui.stomp.client.init;

import org.hui.stomp.client.client.ChatWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class RunOnce implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunOnce.class);

    private final ChatWebSocketClient webSocketClient;

    public RunOnce(ChatWebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (Objects.isNull(webSocketClient.getStompSession()) || !webSocketClient.getStompSession().isConnected()) {
            webSocketClient.connect(); // connect websocket server
            try {
                TimeUnit.SECONDS.sleep(3); // sleep 3 s
            } catch (InterruptedException e) {
                LOGGER.error("wait failed.", e);
            }

        }
    }
}
