package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.service.ChatService;
import org.group4.travelexpertsapi.entity.ChatMessage;
import org.group4.travelexpertsapi.dto.ChatInteractionDTO;
import org.group4.travelexpertsapi.dto.ChatableUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history/{user1}/{user2}")
    public List<ChatMessage> getChatHistory(
            @PathVariable String user1,
            @PathVariable String user2) {
        return chatService.getChatHistory(user1, user2);
    }


    @GetMapping("/interactions")
    public List<ChatInteractionDTO> getUserInteractions(
            @RequestParam String userId) {
        return chatService.getUserInteractions(userId);
    }

    @GetMapping("/contacts")
    public List<ChatableUserDTO> getChatContacts(
            @RequestParam int userId,
            @RequestParam String role) {
        return chatService.getChatContacts(userId, role);
    }


}
