package org.hui.websocket.reliable.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendToUser")
    public void sendToUserByTemplate(Map<String, String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");

        String msg = "from <" + fromUserId + "> message: " + params.get("msg");

        String destination = "/queue/user" + toUserId;
        messagingTemplate.convertAndSend(destination, msg);
    }

    @MessageMapping("/sendToAll")
    public void sendToAll(String msg) {
        String destination = "/queue/chat";
        messagingTemplate.convertAndSend(destination, msg);
    }
}
