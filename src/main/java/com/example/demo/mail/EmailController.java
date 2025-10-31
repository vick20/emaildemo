package com.example.demo.mail;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BrevoEmailService;

import brevo.ApiException;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final BrevoEmailService brevoEmailService;

    public EmailController(EmailService emailService, BrevoEmailService brevoEmailService) {
        this.emailService = emailService;
        this.brevoEmailService = brevoEmailService;
    }

    @GetMapping("/send-test")
    public String sendTestEmail() {
        try {
            emailService.sendHtmlEmail(
                    "famigenafrica@gmail.com",
                    "Test Email via Brevo SMTP",
                    "<h1>Hello from Spring Boot!</h1><p>This is a test HTML email sent via Brevo SMTP.</p>");
            return "✅ Email sent successfully!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "❌ Failed to send: " + e.getMessage();
        }
    }

    @GetMapping("/send-test2")
    public String sendBrevoEmail() {
         try {
            brevoEmailService.sendEmail(
                "famigenafrica@gmail.com",
                "Test Email via Brevo SDk",
                "<h1>Hello from Spring Boot!</h1><p>This is a test HTML email sent via Brevo SMTP.</p>"
            );
            return "✅ Email sent successfully!";
        } catch (ApiException e) {
            e.printStackTrace();
            System.err.println("❌ Brevo API Exception");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response body: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return "❌ Failed to send: " + e.getMessage();
        }
        
    }

}
