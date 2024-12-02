package ru.aveskin.emailnotificationmicroservice.service;

public interface EmailService {
    public void sendMessage(String to, String subject, String text,byte[] attachmentPdf);


}
