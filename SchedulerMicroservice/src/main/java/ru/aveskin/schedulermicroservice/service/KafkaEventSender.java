package ru.aveskin.schedulermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.aveskin.schedulermicroservice.dto.AlertTriggeredEvent;
import ru.aveskin.schedulermicroservice.dto.DailyReportEvent;
import ru.aveskin.schedulermicroservice.dto.ReportResponseDto;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventSender {
    private final KafkaTemplate<String, AlertTriggeredEvent> kafkaTemplateAlertTriggered;
    private final KafkaTemplate<String, DailyReportEvent> kafkaTemplateDailyReport;

    public void sendAlertTriggeredEvent(AlertTriggeredEvent alertTriggeredEvent) {
        String alertId = alertTriggeredEvent.getId().toString();
        sendMessage(kafkaTemplateAlertTriggered, "alert-triggered-events-topic", alertId, alertTriggeredEvent);
    }

    public void sendDailyReportEvent(ReportResponseDto response) {
        String id = response.getEmail();
        DailyReportEvent dailyReportEvent = new DailyReportEvent(response.getEmail(), response.getPdf());
        sendMessage(kafkaTemplateDailyReport, "daily-report-events-topic", id, dailyReportEvent);
    }

    private <T> void sendMessage(KafkaTemplate<String, T> kafkaTemplate, String topic, String key, T payload) {
        CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(topic, key, payload);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Ошибка при отправке сообщения: {}", exception.getMessage());
            } else {
                log.info("Сообщение успешно отправлено: {}", result.getRecordMetadata());
            }
        });
    }
}
