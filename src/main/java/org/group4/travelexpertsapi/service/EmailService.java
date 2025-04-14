package org.group4.travelexpertsapi.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.transaction.Transactional;
import org.group4.travelexpertsapi.entity.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;

@Service
public class EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    @Value("${sendgrid.sender-email}")
    private String sender;

    private final BookingService bookingService;

    public EmailService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedRate = 21600000)
    @Transactional
    public void checkReminder() {
        // Check all bookings for unpaid bookings
        List<Booking> bookings = bookingService.getAllBookings();
        for (Booking booking : bookings) {
            if (booking.getBookingdate() == null && booking.getSavedAt().isAfter(Instant.now().minus(24, ChronoUnit.HOURS))) {
                emailReminder(booking.getCustomerid().getCustemail(), booking);
            } else if (booking.getBookingdate() == null && booking.getSavedAt().isBefore(Instant.now().minus(24, ChronoUnit.HOURS))) {
                emailExpiry(booking.getCustomerid().getCustemail(), booking);
                bookingService.deleteBooking(booking.getBookingno());
            }
        }
    }

    public void emailInvoice(byte[] paymentPDF, String email) {
        Email from = new Email(sender);
        Email to = new Email(email);

        String subject = "Travel Experts - Payment Invoice";
        Content content = new Content("text/plain", "Your payment was successful! The invoice is attached below.");
        Mail mail = new Mail(from, subject, to, content);

        Attachments attachments = new Attachments();
        attachments.setContent(Base64.getEncoder().encodeToString(paymentPDF));
        attachments.setType("application/pdf");
        attachments.setFilename("payment-invoice.pdf");
        attachments.setDisposition("attachment");
        mail.addAttachments(attachments);

        send(mail);
    }

    public void emailReminder(String email, Booking booking) {
        Email from = new Email(sender);
        Email to = new Email(email);

        String subject = "Travel Experts - Payment Reminder";
        String body = "Your reserved booking has not yet been paid for.\n" +
                "You must pay for your booking within 24 hours of placing your reservation, otherwise it will expire.";
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        send(mail);
    }

    private void emailExpiry(String email, Booking booking) {
        Email from = new Email(sender);
        Email to = new Email(email);

        String subject = "Travel Experts - Booking Expired";
        String body = "Your reserved booking has expired since 24 hours has elapsed without payment.\n" +
                "If you would still like to travel with us, feel free to create a new booking on our website.";
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        send(mail);
    }

    private void send(Mail mail) {
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}