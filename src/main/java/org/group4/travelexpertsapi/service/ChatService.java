package org.group4.travelexpertsapi.service;



import org.group4.travelexpertsapi.repository.ChatMessageRepository;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.ChatMessage;
import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.repository.AgentRepository;
import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.group4.travelexpertsapi.dto.ChatInteractionDTO;
import org.group4.travelexpertsapi.dto.ChatableUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired private CustomerRepo customerRepo;
    @Autowired private AgentRepository agentRepo;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(String user1, String user2) {
        return chatMessageRepository.findChatHistory(user1, user2);
    }


    //This method allows user get a list of contacts they are allowed to chat
    public List<ChatableUserDTO> getChatContacts(int userId, String role) {
        List<ChatableUserDTO> results = new ArrayList<>();

        try {
            if (role.equalsIgnoreCase("agent")) {
                List<Customer> customers = customerRepo.findByAgentid_Id(userId);
                for (Customer c : customers) {
                    results.add(new ChatableUserDTO( c.getCustemail(), c.getCustfirstname() + " " + c.getCustlastname(), c.getProfileImageUrl()));


                }
            } else if (role.equalsIgnoreCase("customer")) {
                Customer customer = customerRepo.findById(userId).orElse(null);
                if (customer != null && customer.getAgentid() != null) {
                    Agent agent = agentRepo.findById(customer.getAgentid().getId()).orElse(null);
                    if (agent != null) {
                        results.add(new ChatableUserDTO(agent.getAgtemail(), agent.getAgtfirstname() + " " + agent.getAgtlastname(), agent.getProfileImageUrl()));
                    }
                }
            }
        } catch (Exception e) {
            // Log error if needed
            return Collections.emptyList();
        }

        return results;
    }

    //This gets the list of all previous interactions the user has had

    public List<ChatInteractionDTO> getUserInteractions(String userId) {
        List<ChatInteractionDTO> result = new ArrayList<>();

        try {
            List<ChatMessage> messages = chatMessageRepository.findLatestInteractions(userId);

            for (ChatMessage msg : messages) {
                String otherId = msg.getSenderId().equals(userId) ? msg.getRecipientId() : msg.getSenderId();
                boolean isSender = msg.getSenderId().equals(userId);

                String name = "Unknown";
                String profile = "";


                if (customerRepo.existsByCustemail(otherId)) {
                    Customer c = customerRepo.findByCustemail(otherId);
                    name = c.getCustfirstname() + " " + c.getCustemail();
                    profile = c.getProfileImageUrl();
                } else {
                    if (agentRepo.existsByAgtemail(otherId)) {

                        Agent a = agentRepo.findByAgtemail(otherId);
                        name = a.getAgtfirstname() + " " + a.getAgtlastname();
                        profile = a.getProfileImageUrl();
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


}

