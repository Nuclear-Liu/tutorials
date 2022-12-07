package org.hui.stomp.start.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    /**
     * {@link SimpMessagingTemplate}.
     */
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send") // if have return value will to "/send"
    @SendTo("/topic")
    public String say(String msg) {
        return msg;
    }

    @GetMapping("/send")
    public String msgReply(@RequestParam String msg) {
        messagingTemplate.convertAndSend("/topic", msg);
        return msg;
    }

}
