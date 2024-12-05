package ru.aveskin.schedulermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.aveskin.schedulermicroservice.dto.ReportResponseDto;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyReportSchedulerServiceImpl implements DailyReportSchedulerService{
    private final ApiReportService apiReportService;
    private final KafkaEventSender kafkaEventSender;

    @Override
    public ReportResponseDto getDailyReport() {
        return apiReportService.getDailyReport();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDailyReport() {
        log.info("Попытка получить отчет за день");
        ReportResponseDto dailyReport = getDailyReport();
        if(dailyReport == null){
            log.warn("Отчет не получен");
            return;
        }
        log.info("Отправка отчета в kafka, {}", LocalDate.now());
        kafkaEventSender.sendDailyReportEvent(dailyReport);
    }
}
