package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.service.ChatService;
import org.group4.travelexpertsapi.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload ChatMessage message) {
        ChatMessage savedMessage = chatService.saveMessage(message);

        messagingTemplate.convertAndSendToUser(
                savedMessage.getRecipientId(),
                "/queue/messages",
                savedMessage
        );
    }


}

