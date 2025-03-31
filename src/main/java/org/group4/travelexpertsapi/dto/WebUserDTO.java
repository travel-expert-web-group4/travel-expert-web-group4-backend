package org.group4.travelexpertsapi.dto;

import org.group4.travelexpertsapi.entity.CustomerType;

public class WebUserDTO {

    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private CustomerType type;
    private String profileImage;
    private Integer points;
    private boolean isAgent;

    public WebUserDTO(String email, String password, String fullName, CustomerType type, String profileImage, Integer points, boolean isAgent) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.type = type;
        this.profileImage = profileImage;
        this.points = points;
        this.isAgent = isAgent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public boolean isAgent() {
        return isAgent;
    }

    public void setAgent(boolean agent) {
        isAgent = agent;
    }
}
