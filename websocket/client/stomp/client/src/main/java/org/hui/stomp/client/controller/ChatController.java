package org.hui.stomp.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hui.stomp.client.client.ChatWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private final ChatWebSocketClient webSocketClient;

    public ChatController(ChatWebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @ResponseBody
    @GetMapping("/send")
    public String sendMessage(@RequestParam Map<String, Object> params) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("fromUserId", 100);
        maps.put("toUserId", params.get("userId").toString());
        maps.put("msg", params.get("msg").toString());

        try {
            String json = new ObjectMapper().writeValueAsString(maps);
            webSocketClient.sendMessage(json);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        } finally {
            return "succeed";
        }
    }
}
