package com.example.demo.service;

import brevo.ApiClient;
import brevo.ApiException;
import brevo.Configuration;
import brevo.auth.ApiKeyAuth;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class BrevoEmailService {

    private final TransactionalEmailsApi emailApi;

    // Inject the API key from the environment variable BREVO_API_KEY
    @Value("${brevo.api.key}")
    private String apiKey;

    public BrevoEmailService() {
        ApiClient client = Configuration.getDefaultApiClient();

        // Read the API key from the injected variable
        // Note: We'll set it in a post-construction method
        this.emailApi = new TransactionalEmailsApi(client);
    }

    // Optional: initialize the client after bean creation
    @javax.annotation.PostConstruct
    public void init() {
        ApiClient client = emailApi.getApiClient();
        ApiKeyAuth auth = (ApiKeyAuth) client.getAuthentication("api-key");
        auth.setApiKey(apiKey);  // use the injected value from EMAIL_PASSWORD
    }

    public void sendEmail(String toEmail, String subject, String htmlContent)throws ApiException {
        SendSmtpEmail email = new SendSmtpEmail()
                .sender(new SendSmtpEmailSender()
                        .email("no-reply@famigen.online")  // must be a verified sender
                        .name("sauti54plus-Linkhub"))
                .to(Collections.singletonList(new SendSmtpEmailTo()
                        .email(toEmail)))
                .subject(subject)
                .htmlContent(htmlContent);

       
            emailApi.sendTransacEmail(email);
            System.out.println("✅ Email sent successfully to " + toEmail);
       
    }
}
