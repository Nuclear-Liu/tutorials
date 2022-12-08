package org.hui.websocket.heartbeat.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
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

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        SessionPool.sessions.put(userId, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        if ("ping".equalsIgnoreCase(message)) {
            // Map<String, Object> result = new HashMap<>();
            // result.put("type", "pong");
            // try {
            //     String sResult = new ObjectMapper().writeValueAsString(result);
            //     session.getBasicRemote().sendText(sResult);
            // } catch (IOException e) {
            //     throw new RuntimeException(e);
            // }

            try {
                session.getBasicRemote().sendText("pong");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                Map<String, Object> params = new ObjectMapper().readValue(message, HashMap.class);
                SessionPool.sendMessage(params);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        SessionPool.close(session.getId());
        session.close();
    }
}
