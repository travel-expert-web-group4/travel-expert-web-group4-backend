package org.group4.travelexpertsapi.dto;

import org.group4.travelexpertsapi.entity.Agent;

public class AgentWithPasswordDTO {
    private Agent agent;
    private String password;

    // Getters and setters
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
