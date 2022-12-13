package org.hui.websocket.server.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpoint.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        SessionPool.SESSIONS.put(userId, session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info("from client message: {}", message);

        ObjectMapper mapper = new ObjectMapper();

        if (message.equalsIgnoreCase("ping")) {

            try {
                Map<String, Object> result = new HashMap<>();
                result.put("type", "pong");

                String payload = mapper.writeValueAsString(result);
                session.getBasicRemote().sendText(payload);
            } catch (IOException e) {
                LOGGER.error("pong error.", e);
            }
        } else {
            try {
                Map<String, Object> msg = mapper.readValue(message, HashMap.class);
                SessionPool.sendMessage(msg);
            } catch (JsonProcessingException e) {
                LOGGER.error("result message error.", e);
            }
        }
    }
}
