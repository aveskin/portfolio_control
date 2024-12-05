package ru.aveskin.reportmicroservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aveskin.reportmicroservice.dto.ReportRequestDto;
import ru.aveskin.reportmicroservice.dto.ReportResponseDto;
import ru.aveskin.reportmicroservice.service.ReportGeneratorService;

import java.time.LocalDate;
import java.util.Base64;


@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Tag(name = "Сервис отчетов")
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;

    @Operation(summary = "Получает PDF отчет по истории акции в портфеле, за промежуток времени")
    @GetMapping("/create")
    ResponseEntity<ReportResponseDto> createPdfReport(
            @RequestParam String ticker,
            @RequestParam LocalDate dateLow,
            @RequestParam LocalDate dateHigh,
            @RequestParam String email) {
        try {
            ReportRequestDto request = new ReportRequestDto(ticker, dateLow, dateHigh);

            byte[] pdf = reportGeneratorService.generate(request);
            String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
            ReportResponseDto response = new ReportResponseDto(email, pdfBase64);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
