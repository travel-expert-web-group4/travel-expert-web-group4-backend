package org.group4.travelexpertsapi.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class BookingDTO {

    private String bookingNo;
    private Instant bookingDate;
    private String name;
    private String destination;
    private Instant tripStart;
    private Instant tripEnd;
    private Double travelerCount;
    private String tripTypeId;
    private BigDecimal basePrice;
    private BigDecimal agencyCommission;
    private Instant savedAt;
    private String travelers;

    public BookingDTO() {
    }

    public BookingDTO(String bookingNo, Instant bookingDate, String name, String destination, Instant tripStart, Instant tripEnd, Double travelerCount, String tripTypeId, BigDecimal basePrice, BigDecimal agencyCommission, Instant savedAt, String travelers) {
        this.bookingNo = bookingNo;
        this.bookingDate = bookingDate;
        this.name = name;
        this.destination = destination;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.travelerCount = travelerCount;
        this.tripTypeId = tripTypeId;
        this.basePrice = basePrice;
        this.agencyCommission = agencyCommission;
        this.savedAt = savedAt;
        this.travelers = travelers;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Instant getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Instant bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Instant getTripStart() {
        return tripStart;
    }

    public void setTripStart(Instant tripStart) {
        this.tripStart = tripStart;
    }

    public Instant getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(Instant tripEnd) {
        this.tripEnd = tripEnd;
    }

    public Double getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(Double travelerCount) {
        this.travelerCount = travelerCount;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }

    public Instant getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Instant savedAt) {
        this.savedAt = savedAt;
    }

    public String getTravelers() {
        return travelers;
    }

    public void setTravelers(String travelers) {
        this.travelers = travelers;
    }
}

