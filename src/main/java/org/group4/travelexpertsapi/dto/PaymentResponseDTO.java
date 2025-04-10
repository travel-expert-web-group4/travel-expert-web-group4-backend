package org.group4.travelexpertsapi.dto;

public class PaymentResponseDTO {

    private String status;
    private String message;
    private String sessionid;
    private String sessionUrl;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(String status, String message, String sessionid, String sessionUrl) {
        this.status = status;
        this.message = message;
        this.sessionid = sessionid;
        this.sessionUrl = sessionUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    @Override
    public String toString() {
        return "PaymentResponseDTO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", sessionid='" + sessionid + '\'' +
                ", sessionUrl='" + sessionUrl + '\'' +
                '}';
    }
}
