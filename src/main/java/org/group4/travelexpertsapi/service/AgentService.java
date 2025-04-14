package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.dto.AgentWithPasswordDTO;
import org.group4.travelexpertsapi.entity.Agency;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.User;
import org.group4.travelexpertsapi.repository.AgencyRepository;
import org.group4.travelexpertsapi.repository.AgentRepository;
import org.group4.travelexpertsapi.repository.AgentUserRepository;
import org.group4.travelexpertsapi.utils.AgentPasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final AgencyRepository agencyRepository;
    private final AgentUserRepository agentUserRepository;

    @Value("${agents.image.upload-dir}")
    private String imageUploadDir;


    public AgentService(AgentRepository agentRepository, AgencyRepository agencyRepository, AgentUserRepository agentUserRepository) {
        this.agentRepository = agentRepository;
        this.agencyRepository = agencyRepository;
        this.agentUserRepository = agentUserRepository;
    }


    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }


    public Agent getAgentById(Integer id) {
        return agentRepository.findById(id).orElse(null);
    }

    public Agent getAgentByEmail(String email) {
        return agentRepository.findByAgtemail(email);
    }


    public Agent createAgent(AgentWithPasswordDTO dto, MultipartFile image) throws IOException {
        Agent agent = dto.getAgent();

        if (agent.getAgencyid() != null && agent.getAgencyid().getId() != null) {
            Agency existingAgency = agencyRepository.findById(agent.getAgencyid().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Agency not found"));
            agent.setAgencyid(existingAgency);
        } else {
            throw new IllegalArgumentException("Agency ID is required");
        }

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(agent.getAgtemail(), image);
            agent.setProfileImageUrl(imagePath);
        }

        Agent savedAgent = agentRepository.save(agent);

        // Save login credentials
        saveAgentToUsers(savedAgent, dto.getPassword());

        return savedAgent;
    }

    public void saveAgentToUsers(Agent agent, String password) {
        User user = new User();
        user.setId(agent.getId());
        user.setEmail(agent.getAgtemail());
        user.setPasswordHash(AgentPasswordUtils.hashPassword(password)); // Recommended: hash passwords!
        user.setRole(agent.getRole());
        user.setAgentid(agent);

        agentUserRepository.save(user);
    }



    public Agent updateAgent(Integer id, Agent updatedAgent, MultipartFile image) throws IOException {
        Agent existing = getAgentById(id);
        if (existing == null) return null;

        // Resolve and reattach agency entity to avoid TransientObjectException
        if (updatedAgent.getAgencyid() != null && updatedAgent.getAgencyid().getId() != null) {
            Agency existingAgency = agencyRepository.findById(updatedAgent.getAgencyid().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Agency not found"));
            existing.setAgencyid(existingAgency);
        } else {
            throw new IllegalArgumentException("Agency ID is required");
        }

        // Update fields
        existing.setAgtfirstname(updatedAgent.getAgtfirstname());
        existing.setAgtmiddleinitial(updatedAgent.getAgtmiddleinitial());
        existing.setAgtlastname(updatedAgent.getAgtlastname());
        existing.setAgtbusphone(updatedAgent.getAgtbusphone());
        existing.setAgtemail(updatedAgent.getAgtemail());
        existing.setAgtposition(updatedAgent.getAgtposition());
        existing.setStatus(updatedAgent.getStatus());
        existing.setRole(updatedAgent.getRole());

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(existing.getAgtemail(), image);
            existing.setProfileImageUrl(imagePath);
        }

        return agentRepository.save(existing);
    }


    public void deleteAgent(Integer id) {
        agentRepository.deleteById(id);
    }

    private String saveImage(String email, MultipartFile image) throws IOException {
        String fileName = email.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
        Path folderPath = Paths.get(imageUploadDir);
        if (!Files.exists(folderPath)) Files.createDirectories(folderPath);
        Path filePath = folderPath.resolve(fileName);
        Files.write(filePath, image.getBytes());
        return "/images/agents/" + fileName;
    }

    public Agent updateAgentPhoto(Integer id, MultipartFile image) throws IOException {
        Agent existing = getAgentById(id);
        if (existing == null) return null;

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(existing.getAgtemail(), image);
            existing.setProfileImageUrl(imagePath);
        }

        return agentRepository.save(existing);
    }
}

