package ru.aveskin.reportmicroservice.service;

import ru.aveskin.reportmicroservice.dto.ReportRequestDto;

public interface ReportGeneratorService {
    byte[] generate(ReportRequestDto request);
}
