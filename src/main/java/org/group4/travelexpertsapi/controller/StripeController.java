package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.dto.PaymentRequestDTO;
import org.group4.travelexpertsapi.dto.PaymentResponseDTO;
import org.group4.travelexpertsapi.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDTO> checkout(@RequestBody BookingDTO booking) {
         PaymentResponseDTO response = stripeService.checkout(booking);
         // Email PDF with payment information
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payment-success")
    public ResponseEntity<Void> success() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
