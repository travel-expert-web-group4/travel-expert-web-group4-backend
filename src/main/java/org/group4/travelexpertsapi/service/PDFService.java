package org.group4.travelexpertsapi.service;

import com.stripe.model.LineItem;
import com.stripe.model.checkout.Session;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PDFService {

    public byte[] generatePaymentPDF(Session session) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 750);

            contentStream.showText("Payment Invoice");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Session ID: " + session.getId());
            contentStream.newLine();
            contentStream.showText("Payment Status: " + session.getPaymentStatus());
            contentStream.newLine();
            for (LineItem items : session.getLineItems().getData()) {
                long convertPrice = items.getPrice().getUnitAmount() / 100;
                contentStream.showText(items.getDescription() + " x " + items.getQuantity() + " - $" + convertPrice + " " + items.getCurrency().toUpperCase());
                contentStream.newLine();
            }
            contentStream.showText("Subtotal: $" + session.getAmountSubtotal() + " " + session.getCurrency().toUpperCase());
            contentStream.newLine();
            contentStream.showText("Total: $" + session.getAmountTotal() + " " + session.getCurrency().toUpperCase());
            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
