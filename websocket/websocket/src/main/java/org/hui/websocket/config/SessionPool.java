package org.hui.websocket.config;

import javax.websocket.Session;
import java.util.Map;
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

}
