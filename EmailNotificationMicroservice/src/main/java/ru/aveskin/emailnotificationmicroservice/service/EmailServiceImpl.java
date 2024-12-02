package ru.aveskin.emailnotificationmicroservice.service;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private JavaMailSender emailSender;

    @Override
    @Async
    public void sendMessage(String to, String subject, String text, byte[] attachmentPdf) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if (attachmentPdf != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentPdf, "application/pdf");
                helper.addAttachment("report.pdf", dataSource);
            }
//            emailSender.send(message); пока нет настроек smtp буду просто кидать сообщение в лог
            log.info("Notification was sent to email: " + to);
        } catch (jakarta.mail.MessagingException e) {
            log.error("Error while sending out email..{}", (Object) e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}
