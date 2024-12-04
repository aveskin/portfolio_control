package ru.aveskin.schedulermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.aveskin.schedulermicroservice.dto.AlertTriggeredEvent;


import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventSender {
    private final KafkaTemplate<String, AlertTriggeredEvent> kafkaTemplate;

    public void sendAlertTriggeredEvent(AlertTriggeredEvent alertTriggeredEvent) {
        String alertId = alertTriggeredEvent.getId().toString();

        //При асинхронной обрабоотке
        CompletableFuture<SendResult<String, AlertTriggeredEvent>> future =
                kafkaTemplate.send("alert-triggered-events-topic", alertId, alertTriggeredEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send message: {}", exception.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata());
            }
        });
    }
}
