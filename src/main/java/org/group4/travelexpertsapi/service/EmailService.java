package org.group4.travelexpertsapi.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    public Response emailInvoice(byte[] paymentPDF, String email) {
        Email from = new Email("ryan.angaangan@edu.sait.ca");
        Email to = new Email(email);

        String subject = "Payment Invoice";
        Content content = new Content("text/plain", "Payment Invoice");
        Mail mail = new Mail(from, subject, to, content);

        Attachments attachments = new Attachments();
        attachments.setContent(Base64.getEncoder().encodeToString(paymentPDF));
        attachments.setType("application/pdf");
        attachments.setFilename("payment-invoice.pdf");
        attachments.setDisposition("attachment");
        mail.addAttachments(attachments);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            return sg.api(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
