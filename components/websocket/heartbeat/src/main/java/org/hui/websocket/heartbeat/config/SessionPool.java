package org.hui.websocket.heartbeat.config;

import javax.websocket.Session;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SessionPool {

    /**
     * kye: userId, value: {@link Session}.
     */
    public static Map<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * close connection session, remove session in sessions.
     *
     * @param sessionId {@link Session}'s id.
     */
    public static void close(String sessionId) {
        for (String userId : sessions.keySet()) {
            Session session = sessions.get(userId);
            if (session.getId().equals(sessionId)) {
                sessions.remove(userId);
                break;
            }
        }
    }

    /**
     * send message to user(by user's id).
     *
     * @param userId  user's id.
     * @param message send message.
     */
    public static void sendMessage(String userId, String message) {
        sessions.get(userId).getAsyncRemote().sendText(message);
    }

    /**
     * broadcast.
     *
     * @param message broadcast message.
     */
    public static void sendMessage(String message) {
        for (String userId : sessions.keySet()) {
            sessions.get(userId).getAsyncRemote().sendText(message);
        }
    }

    public static void sendMessage(Map<String, Object> payload) {
        String toUserId = payload.get("toUserId").toString();
        String msg = payload.get("msg").toString();
        String fromUserId = payload.get("fromUserId").toString();

        Session session = sessions.get(toUserId);

        if (Objects.nonNull(session)) {
            session.getAsyncRemote().sendText("来自<" + fromUserId + ">消息：" + msg);
        }
    }

}
