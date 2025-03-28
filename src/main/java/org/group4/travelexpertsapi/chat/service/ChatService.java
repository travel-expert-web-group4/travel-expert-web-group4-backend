package org.group4.travelexpertsapi.chat.service;



import org.group4.travelexpertsapi.chat.repository.ChatMessageRepository;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.ChatMessage;
import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.viewmodel.ChatInteractionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
//    @Autowired private CustomerRepository customerRepo;
//    @Autowired private AgentRepository agentRepo;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(String user1, String user2) {
        return chatMessageRepository.findChatHistory(user1, user2);
    }


    //This method allows user get a list of contacts they are allowed to chat
//    public List<ChatableUserDTO> getChatContacts(Long userId, String role) {
//        List<ChatableUserDTO> results = new ArrayList<>();
//
//        try {
//            if (role.equalsIgnoreCase("agent")) {
//                List<Customer> customers = customerRepo.findByAgentId(userId);
//                for (Customer c : customers) {
//                    results.add(new ChatableUserDTO(c.getId(), c.getFirstName() + " " + c.getLastName(), c.getProfilePicture()));
//                }
//            } else if (role.equalsIgnoreCase("customer")) {
//                Customer customer = customerRepo.findById(userId).orElse(null);
//                if (customer != null && customer.getAgentId() != null) {
//                    Agent agent = agentRepo.findById(customer.getAgentId()).orElse(null);
//                    if (agent != null) {
//                        results.add(new ChatableUserDTO(agent.getId(), agent.getName(), agent.getProfilePicture()));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // Log error if needed
//            return Collections.emptyList();
//        }
//
//        return results;
//    }

    //This gets the list of all previous interactions the user has had
    /*
    public List<ChatInteractionDTO> getUserInteractions(String userId) {
        List<ChatInteractionDTO> result = new ArrayList<>();

        try {
            List<ChatMessage> messages = chatMessageRepository.findLatestInteractions(userId);

            for (ChatMessage msg : messages) {
                String otherId = msg.getSenderId().equals(userId) ? msg.getRecipientId() : msg.getSenderId();
                boolean isSender = msg.getSenderId().equals(userId);

                String name = "Unknown";
                String profile = "";

                Optional<Customer> c = customerRepo.findById(otherId);
                if (c.isPresent()) {
                    name = c.get().getFirstName() + " " + c.get().getLastName();
                    profile = c.get().getProfilePicture();
                } else {
                    Optional<Agent> a = agentRepo.findById(otherId);
                    if (a.isPresent()) {
                        name = a.get().getName();
                        profile = a.get().getProfilePicture();
                    }
                }

                result.add(new ChatInteractionDTO(otherId, name, profile, msg.getContent(), isSender));
            }
        } catch (Exception e) {
            // Log error if needed
            return Collections.emptyList();
        }

        return result;
    }

     */
}

