package com.example.demo.mail;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-test")
    public String sendTestEmail() {
        try {
            emailService.sendHtmlEmail(
                "famigenafrica@gmail.com",
                "Test Email via Brevo SMTP",
                "<h1>Hello from Spring Boot!</h1><p>This is a test HTML email sent via Brevo SMTP.</p>"
            );
            return "✅ Email sent successfully!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "❌ Failed to send: " + e.getMessage();
        }
    }
}
