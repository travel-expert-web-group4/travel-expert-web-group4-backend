package org.group4.travelexpertsapi.dto;

public class ChatInteractionDTO {
    private String otherUserId;
    private String name;
    private String profilePicture;
    private String lastMessage;
    private boolean isUserTheLastSender;

    public ChatInteractionDTO() {}
    public ChatInteractionDTO(String otherUserId, String name, String profilePicture, String lastMessage, boolean isUserTheLastSender) {
        this.otherUserId = otherUserId;
        this.name = name;
        this.profilePicture = profilePicture;
        this.lastMessage = lastMessage;
        this.isUserTheLastSender = isUserTheLastSender;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isUserTheLastSender() {
        return isUserTheLastSender;
    }

    public void setUserTheLastSender(boolean userTheLastSender) {
        isUserTheLastSender = userTheLastSender;
    }
}

