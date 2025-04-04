package org.group4.travelexpertsapi.dto;

public class ChatableUserDTO {
    private String userEmail;
    private String name;
    private String profilePicture;

    public ChatableUserDTO(String userEmail, String name, String profilePicture) {
        this.userEmail = userEmail;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
