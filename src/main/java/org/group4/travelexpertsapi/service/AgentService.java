package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Agency;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.repository.AgencyRepository;
import org.group4.travelexpertsapi.repository.AgentRepository;
import org.group4.travelexpertsapi.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final AgencyRepository agencyRepository;

    @Value("${agents.image.upload-dir}")
    private String imageUploadDir;


    public AgentService(AgentRepository agentRepository, AgencyRepository agencyRepository) {
        this.agentRepository = agentRepository;
        this.agencyRepository = agencyRepository;
    }


    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }


    public Agent getAgentById(Integer id) {
        return agentRepository.findById(id).orElse(null);
    }


    public Agent createAgent(Agent agent, MultipartFile image) throws IOException {
        if (agent.getAgencyid() != null && agent.getAgencyid().getId() != null) {
            // Fetch the persistent agency object
            Agency existingAgency = agencyRepository.findById(agent.getAgencyid().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Agency not found"));

            // Set the managed agency object back
            agent.setAgencyid(existingAgency);
        } else {
            throw new IllegalArgumentException("Agency ID is required");
        }


        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(agent.getAgtemail(), image);
            agent.setProfileImageUrl(imagePath);
        }

        return agentRepository.save(agent);
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
}

