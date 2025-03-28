package org.group4.travelexpertsapi.viewmodel;

public class ChatInteractionDTO {
    private Long otherUserId;
    private String name;
    private String profilePicture;
    private String lastMessage;
    private boolean isUserTheLastSender;

    public ChatInteractionDTO() {}
    public ChatInteractionDTO(Long otherUserId, String name, String profilePicture, String lastMessage, boolean isUserTheLastSender) {
        this.otherUserId = otherUserId;
        this.name = name;
        this.profilePicture = profilePicture;
        this.lastMessage = lastMessage;
        this.isUserTheLastSender = isUserTheLastSender;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Long otherUserId) {
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

