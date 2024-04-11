package com.glos.notificationservice.manager;

import com.glos.notificationservice.domain.entity.Message;
import com.glos.notificationservice.domain.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailManager
{
    private final EmailService emailService;

    public EmailManager(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendVerificationMessage(Message message, Map<String, String> data) throws MessagingException
    {
        emailService.sendVerificationHtml(message, data);
    }

    public void sendMessage(Message message, Map<String, String> data) throws MessagingException
    {
        emailService.sendMessageHtml(message, data);
    }
}
