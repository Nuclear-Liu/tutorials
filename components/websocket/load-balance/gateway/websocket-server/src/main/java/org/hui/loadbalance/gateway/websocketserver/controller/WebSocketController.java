package org.hui.loadbalance.gateway.websocketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendToAll")
    public String sendToAll(String msg) {
        return msg;
    }

    @MessageMapping("/send")
    @SendTo("/topic")
    public String say(String msg) {
        return msg;
    }

    @MessageMapping("/sendToUser")
    public void sendToUserByTemplate(Map<String, String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");

        String msg = "from <" + fromUserId + "> message:" + params.get("msg");

        String destination = "/queue/user_" + toUserId;
        messagingTemplate.convertAndSend(destination, msg);
    }

    @GetMapping("/sendToAllByTemplate")
    @MessageMapping("/sendToAllByTemplate")
    public void sendToAllByTemplate(@RequestParam String msg) {
        messagingTemplate.convertAndSend("/topic/chat", msg);
    }

    @GetMapping("/send")
    public String msgReply(@RequestParam String msg) {
        messagingTemplate.convertAndSend("/topic", msg);
        return msg;
    }
}
