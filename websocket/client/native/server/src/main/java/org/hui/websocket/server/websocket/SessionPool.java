package org.hui.websocket.server.websocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SessionPool {
    /**
     * key: userId, value: {@link Session}.
     */
    public static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public static void close(String userId) throws IOException {
        Session session = SESSIONS.get(userId);
        if (Objects.nonNull(session)) {
            session.close();
        }
    }

    public static void sendMessage(String userId, String message) {
        SESSIONS.get(userId).getAsyncRemote().sendText(message);
    }

    public static void sendMessage(String message) {
        for (String userId : SESSIONS.keySet()) {
            SESSIONS.get(userId).getAsyncRemote().sendText(message);
        }
    }

    public static void sendMessageByUserId(String userId, String message) {
        Session session = SESSIONS.get(userId);
        if (Objects.nonNull(session) && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    public static String sendMessage(Map<String, Object> params) {
        String msg = params.get("msg").toString();
        String fromUserId = params.get("fromUserId").toString();

        if (Objects.nonNull(params.get("toUserId")) && !params.get("toUserId").equals("")) {
            String toUserId = params.get("toUserId").toString();

            String payload = String.format("from <%s> to <%s> message:%s", fromUserId, toUserId, msg);
            sendMessageByUserId(toUserId, msg);
        } else {
            String payload = String.format("from <%s> to all message:%s", fromUserId, msg);
            sendMessage(msg);
        }
        return "send succeed";
    }

}
