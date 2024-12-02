package ru.aveskin.emailnotificationmicroservice.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import ru.aveskin.emailnotificationmicroservice.dto.AlertTriggeredEvent;
import ru.aveskin.emailnotificationmicroservice.service.EmailService;

@Component
@KafkaListener(topics = "alert-triggered-events-topic")
@Slf4j
@RequiredArgsConstructor
public class AlertTriggeredEventHandler {
    private final EmailService emailService;

    @KafkaHandler
    public void handle(AlertTriggeredEvent alertTriggeredEvent) {
        String email = alertTriggeredEvent.getUserEmail();

        log.info("Received event price for: {}", alertTriggeredEvent.getTicker());

        try {
            String text = "Цена акции " + alertTriggeredEvent.getTicker() + " достигла целевого значения = "
                    + alertTriggeredEvent.getAimPrice();
            emailService.sendMessage(email, "Цена достигла целевого значения!", text, null);
            log.info("Email was sent to: {}", email);
        } catch (MessagingException e) {
            log.error("Error while sending out email..{}", (Object) e.getStackTrace());
        }


    }
}
