package ru.aveskin.emailnotificationmicroservice.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import ru.aveskin.emailnotificationmicroservice.dto.AlertTriggeredEvent;
import ru.aveskin.emailnotificationmicroservice.dto.DalyReportCreatedEvent;
import ru.aveskin.emailnotificationmicroservice.service.EmailService;

@Component
@KafkaListener(topics = "alert-triggered-events-topic",
        groupId = "daly-report-created-events",
        containerFactory = "kafkaListenerContainerFactory2")
@Slf4j
@RequiredArgsConstructor
public class DalyReportCreatedEventHandler {
    private final EmailService emailService;

    @KafkaHandler
    public void handle(DalyReportCreatedEvent dalyReportCreatedEvent) {
        String email = dalyReportCreatedEvent.getEmail();

        log.info("Received event price for: {}", email);

        try {
            byte[] attachmentPdf = dalyReportCreatedEvent.getPdf().getBytes();
            String text = "Ежкдневная рассылка отчета по вашим операциям";
            emailService.sendMessage(email, "Daly report", text, attachmentPdf);
            log.info("Email was sent to: {}", email);
        } catch (MessagingException e) {
            log.error("Error while sending out email..{}", (Object) e.getStackTrace());
        }
    }
}
