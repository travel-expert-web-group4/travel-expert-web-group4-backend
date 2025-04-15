package org.group4.travelexpertsapi.controller;

import com.stripe.model.checkout.Session;
import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.dto.PaymentResponseDTO;
import org.group4.travelexpertsapi.service.BookingService;
import org.group4.travelexpertsapi.service.EmailService;
import org.group4.travelexpertsapi.service.PDFService;
import org.group4.travelexpertsapi.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    private final StripeService stripeService;
    private final PDFService pdfService;
    private final EmailService emailService;
    private final BookingService bookingService;


    public StripeController(StripeService stripeService, PDFService pdfService, EmailService emailService,   BookingService bookingService ) {
        this.stripeService = stripeService;
        this.pdfService = pdfService;
        this.emailService = emailService;
        this.bookingService = bookingService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDTO> checkout(@RequestBody BookingDTO booking) {
         PaymentResponseDTO response = stripeService.checkout(booking);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payment-success")
    public RedirectView success(@RequestParam("session_id") String sessionId) {
        Session session = stripeService.getSession(sessionId);

        // ✅ Get bookingNo from metadata
        String bookingNo = session.getMetadata().get("bookingNo");

        // ✅ Set booking date and savedAt
        if (bookingNo != null) {
            bookingService.setBookingDate(bookingNo);
        }



        // Generate PDF with payment information
        byte[] paymentPDF = pdfService.generatePaymentPDF(session);
        // Email the PDF
        emailService.emailInvoice(paymentPDF, session.getCustomerDetails().getEmail());
        return new RedirectView("http://localhost:5173/my-bookings");
    }

    @GetMapping("/payment-cancel")
    public RedirectView cancel() {
        return new RedirectView("http://localhost:5173/my-bookings");
    }
}
