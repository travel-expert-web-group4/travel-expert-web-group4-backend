package org.group4.travelexpertsapi.chat.controller;

import org.group4.travelexpertsapi.chat.service.ChatService;
import org.group4.travelexpertsapi.entity.ChatMessage;
import org.group4.travelexpertsapi.viewmodel.ChatableUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {

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

//    @GetMapping("/contacts")
//    public List<ChatableUserDTO> getChatContacts(
//            @RequestParam Long userId,
//            @RequestParam String role) {
//        return chatService.getChatContacts(userId, role);
//    }
}

