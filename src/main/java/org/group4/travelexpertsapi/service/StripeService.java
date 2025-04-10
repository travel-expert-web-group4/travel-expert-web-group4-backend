package org.group4.travelexpertsapi.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.dto.PaymentRequestDTO;
import org.group4.travelexpertsapi.dto.PaymentResponseDTO;
import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.repository.PackageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    private PackageRepo packageRepo;

    public StripeService(PackageRepo packageRepo) {
        this.packageRepo = packageRepo;
    }

    public PaymentResponseDTO checkout(BookingDTO booking) {
        Stripe.apiKey = secretKey;

        // Convert incoming booking to PaymentRequestDTO
        PaymentRequestDTO request = convertBookingDTO(booking);

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(request.getProductName())
                .setDescription(request.getDescription())
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(request.getCurrency())
                .setUnitAmount(request.getAmount())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(request.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams sessionParams = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/stripe/payment-success")
                .setCancelUrl("http://localhost:8080/payment-cancel")
                .addLineItem(lineItem)
                .build();

        Session session;

        try {
            session = Session.create(sessionParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setStatus(session.getStatus());
        response.setMessage("Created Payment Session");
        response.setSessionid(session.getId());
        response.setSessionUrl(session.getUrl());

        return response;
    }

    private PaymentRequestDTO convertBookingDTO(BookingDTO booking) {
        PaymentRequestDTO request = new PaymentRequestDTO();
        // Grab package to extract description
        Package pkg = packageRepo.findByPkgname(booking.getName());
        Long convertAmount = booking.getBasePrice().add(booking.getAgencyCommission()).setScale(0, RoundingMode.HALF_UP).longValue() * 100;
        request.setAmount(convertAmount);
        request.setCurrency("CAD");
        request.setProductName(booking.getName());
        request.setDescription(pkg.getPkgdesc());
        request.setQuantity(booking.getTravelerCount().longValue());
        return request;
    }
}
