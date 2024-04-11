package com.glos.notificationservice.domain.DTO;

import com.glos.notificationservice.domain.entity.Content;

import java.util.Map;

public class MessageVerificationDTO
{
    private String to;
    private String subject;
    private Content text;
    private Map<String, String> data;


    public Content getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public Map<String, String> getData() {
        return data;
    }

    public MessageVerificationDTO(){}
}
