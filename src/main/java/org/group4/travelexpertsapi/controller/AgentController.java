package org.group4.travelexpertsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group4.travelexpertsapi.dto.AgentWithPasswordDTO;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.service.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = Logger.getLogger(AgentController.class.getName());

    public AgentController(AgentService agentService, ObjectMapper objectMapper) {
        this.agentService = agentService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Agent> getAllAgents() {
        return agentService.getAllAgents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable Integer id) {
        Agent agent = agentService.getAgentById(id);
        return agent != null ? ResponseEntity.ok(agent) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email")
    public ResponseEntity<Agent> getAgentByEmail(@RequestPart("email") String email) {
        Agent agent = agentService.getAgentByEmail(email);
        return agent != null ? ResponseEntity.ok(agent) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createAgent(
            @RequestPart("agent") String agentJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            LOGGER.info("Received Agent JSON: " + agentJson);


            AgentWithPasswordDTO dto = objectMapper.readValue(agentJson, AgentWithPasswordDTO.class);

            Agent saved = agentService.createAgent(dto, image);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "JSON Parsing Error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected Error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateAgent(
            @PathVariable Integer id,
            @RequestPart("agent") String agentJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            LOGGER.info("Received Agent JSON for Update: " + agentJson);
            Agent agent = objectMapper.readValue(agentJson, Agent.class);
            Agent updated = agentService.updateAgent(id, agent, image);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "JSON Parsing Error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected Error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Integer id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/update-picture", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateAgent(
            @PathVariable Integer id,

            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {

            Agent updated = agentService.updateAgentPhoto(id, image);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "JSON Parsing Error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected Error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
