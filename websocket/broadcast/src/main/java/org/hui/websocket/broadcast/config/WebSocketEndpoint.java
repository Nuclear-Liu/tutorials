package org.hui.websocket.broadcast.config;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/broadcast/{userId}")
public class WebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        SessionPool.sessions.put(userId, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        SessionPool.sendMessage(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        SessionPool.close(session.getId());
        session.close();
    }
}
