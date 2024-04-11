package com.glos.notificationservice.domain.service;

import com.glos.notificationservice.domain.entity.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.naming.*;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;


    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine)
    {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    public void sendVerificationHtml(Message letter, Map<String, String> data) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();

        context.setVariable("title", letter.getText().getTitle());
        context.setVariable("mainText", letter.getText().getMainText());
        context.setVariable("code", data.get("code"));

        String process = templateEngine.process(data.get("viewName"), context);

        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());

        helper.setTo(letter.getTo());
        helper.setFrom(username);
        helper.setSubject(letter.getSubject());
        helper.setText(process, true);

        mailSender.send(message);
    }

    public void sendMessageHtml(Message letter, Map<String, String> data) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();

        context.setVariable("title", letter.getText().getTitle());
        context.setVariable("mainText", letter.getText().getMainText());

        String process = templateEngine.process(data.get("viewName"), context);

        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());

        helper.setTo(letter.getTo());
        helper.setFrom(username);
        helper.setSubject(letter.getSubject());
        helper.setText(process, true);

        mailSender.send(message);
    }
}

/*
DTO
* */
