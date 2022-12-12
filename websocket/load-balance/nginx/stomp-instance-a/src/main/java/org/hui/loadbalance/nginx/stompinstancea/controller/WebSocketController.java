package org.hui.loadbalance.nginx.stompinstancea.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendToAll")
    public String sendToAll(String msg) {
        return msg;
    }

    @MessageMapping("/sendToUser")
    public void sendToUser(Map<String, String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");

        String msg = "来自<" + fromUserId + ">消息：" + params.get("msg");

        String destination = "/queue/user_" + toUserId;

        LOGGER.info("host: 8801");

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
