package com.glos.api.operationservice;

import com.glos.api.operationservice.client.NotificationClient;
import com.glos.api.operationservice.dto.MessageVerificationDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    private final NotificationClient client;

    public NotificationService(NotificationClient client) {
        this.client = client;
    }

    public void send(String to, Action action, Map<String, String> data) {
        MessageVerificationDTO message = new MessageVerificationDTO();
        message.setTo(to);
        message.setData(data);
        message.setSubject("");
        client.sendTemplate(action.getNameLowercase(), message);
    }

}
