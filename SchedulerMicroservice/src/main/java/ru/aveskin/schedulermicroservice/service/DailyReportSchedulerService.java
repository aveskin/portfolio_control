package ru.aveskin.schedulermicroservice.service;

import ru.aveskin.schedulermicroservice.dto.ReportResponseDto;

public interface DailyReportSchedulerService {
    ReportResponseDto getDailyReport();
    void sendDailyReport();
}
