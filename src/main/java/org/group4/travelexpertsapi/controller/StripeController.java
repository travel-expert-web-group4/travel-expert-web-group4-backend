package org.group4.travelexpertsapi.controller;

import com.stripe.model.checkout.Session;
import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.dto.PaymentResponseDTO;
import org.group4.travelexpertsapi.service.PDFService;
import org.group4.travelexpertsapi.service.StripeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    private final StripeService stripeService;
    private final PDFService pdfService;

    public StripeController(StripeService stripeService, PDFService pdfService) {
        this.stripeService = stripeService;
        this.pdfService = pdfService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDTO> checkout(@RequestBody BookingDTO booking) {
         PaymentResponseDTO response = stripeService.checkout(booking);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payment-success")
    public ResponseEntity<Void> success(@RequestParam("session_id") String sessionId) {
        Session session = stripeService.getSession(sessionId);
        System.out.println(session.getLineItems().getData());
        // Generate PDF with payment information
        byte[] paymentPDF = pdfService.generatePaymentPDF(session);
        // Email the PDF
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
