package org.group4.travelexpertsapi.chat.controller;

import org.group4.travelexpertsapi.chat.service.ChatService;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.ChatMessage;
import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.viewmodel.ChatInteractionDTO;
import org.group4.travelexpertsapi.viewmodel.ChatableUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history/{user1}/{user2}")
    public List<ChatMessage> getChatHistory(
            @PathVariable String user1,
            @PathVariable String user2) {
        return chatService.getChatHistory(user1, user2);
    }


//    @GetMapping("/interactions")
//    public List<ChatInteractionDTO> getUserInteractions(
//            @RequestParam String userId) {
//        return chatService.getUserInteractions(userId);
//    }

}
