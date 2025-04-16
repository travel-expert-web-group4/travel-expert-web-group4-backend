package org.group4.travelexpertsapi.service;

import com.stripe.model.LineItem;
import com.stripe.model.checkout.Session;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.group4.travelexpertsapi.entity.Booking;
import org.group4.travelexpertsapi.repository.BookingRepo;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class PDFService {

    private final BookingRepo bookingRepo;

    public PDFService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public byte[] generatePaymentPDF(Session session) {
        // Load the corresponding booking to pull additional details
        Booking booking = bookingRepo.findByBookingno(session.getMetadata().get("bookingNo"));

        try (PDDocument document = new PDDocument()) {
            // Load Travel Experts logo
            InputStream stream = getClass().getClassLoader().getResourceAsStream("pdf/logo.png");
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, stream.readAllBytes(), "logo.png");

            // Initialize page object and set initial font
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
            contentStream.beginText();
            // Set line spacing and initial line position
            contentStream.setLeading(18f);
            contentStream.newLineAtOffset(50, 730);

            // Top line before divider
            contentStream.showText("Travel Experts - Stripe Payment Invoice");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.newLine();

            // PDF body - reset font
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.showText("Session ID: " + session.getId());
            contentStream.newLine();
            contentStream.showText("Payment Status: " + session.getPaymentStatus());
            contentStream.newLine();
            contentStream.showText("Paid at: " + Instant.ofEpochSecond(session.getPaymentIntentObject().getCreated())
                    .atZone(ZoneId.of("America/Edmonton"))
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy")));
            contentStream.newLine();
            contentStream.newLine();
            if (booking != null) {
                contentStream.showText("Booking #: " + booking.getBookingno());
                contentStream.newLine();
                contentStream.showText("# of Travelers: " + booking.getTravelercount().intValue());
                contentStream.newLine();
                contentStream.showText("Travelers: " + booking.getTravelers());
                contentStream.newLine();
                contentStream.newLine();
            }
            for (LineItem items : session.getLineItems().getData()) {
                long convertPrice = items.getPrice().getUnitAmount() / 100;
                contentStream.showText("Package Purchased: " + items.getDescription() + " x " + items.getQuantity()
                        + " - $" + convertPrice + " " + items.getCurrency().toUpperCase());
                contentStream.newLine();
            }
            contentStream.newLine();
            contentStream.showText("Subtotal: $" + session.getAmountSubtotal() / 100 + " " + session.getCurrency().toUpperCase());
            contentStream.newLine();
            contentStream.showText("Total: $" + session.getAmountTotal() / 100 + " " + session.getCurrency().toUpperCase());
            contentStream.newLine();
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Invoice generated at: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy")));
            contentStream.endText();

            // Draw logo and divider line
            contentStream.drawImage(logo, 475f, 675f, 100f, 100f);
            contentStream.moveTo(50, 720);
            contentStream.lineTo(462, 720);
            contentStream.setLineWidth(2f);
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.stroke();

            // Close the PDF and save changes
            contentStream.close();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
