package com.glos.notificationservice.controller;
import com.glos.notificationservice.domain.DTO.MessageDTO;
import com.glos.notificationservice.domain.entity.Content;
import com.glos.notificationservice.domain.entity.Message;
import com.glos.notificationservice.domain.DTO.MessageVerificationDTO;
import com.glos.notificationservice.manager.EmailManager;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController
{

    private final EmailManager emailManager;


    public EmailController(EmailManager emailManager)
    {
        this.emailManager = emailManager;
    }


    @GetMapping("/send/change-email")
    public HttpStatus sendEmail(@RequestBody MessageVerificationDTO messageDTO, Model model) throws MessagingException
    {
        Message message = new Message(messageDTO.getTo(), messageDTO.getSubject(),
                messageDTO.getText(), model);
        emailManager.sendVerificationMessage(message, messageDTO.getData());

        return HttpStatus.OK;
    }

    @GetMapping("/send/message-email")
    public HttpStatus sendMessage(@RequestBody MessageDTO messageDTO, Model model) throws MessagingException
    {
        Message message = new Message(messageDTO.getTo(), messageDTO.getSubject(),
                messageDTO.getText(), model);
        emailManager.sendVerificationMessage(message, messageDTO.getData());

        return HttpStatus.OK;
    }

}
