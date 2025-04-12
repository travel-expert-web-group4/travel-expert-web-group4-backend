package org.group4.travelexpertsapi.dto;

public class PaymentRequestDTO {

    private String productName;
    private String description;
    private String currency;
    private Long amount;
    private Long quantity;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(Long quantity, Long amount, String currency, String productName, String description) {
        this.quantity = quantity;
        this.amount = amount;
        this.currency = currency;
        this.productName = productName;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "productName='" + productName + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", quantity=" + quantity +
                '}';
    }
}