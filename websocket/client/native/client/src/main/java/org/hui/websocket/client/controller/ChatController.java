package org.hui.websocket.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hui.websocket.client.client.ChatWebSocketClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {

    private final ChatWebSocketClient webSocketClient;

    public ChatController(ChatWebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @ResponseBody
    @GetMapping("/send")
    public String sendMessage(@RequestParam Map<String, Object> params) throws JsonProcessingException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("fromUserId", 100);
        maps.put("toUserId", params.get("userId").toString());
        maps.put("msg", params.get("msg").toString());
        String json = new ObjectMapper().writeValueAsString(maps);

        webSocketClient.send(json);

        return "succeed";
    }
}
