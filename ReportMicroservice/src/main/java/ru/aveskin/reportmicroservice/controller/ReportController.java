package ru.aveskin.reportmicroservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aveskin.reportmicroservice.dto.ReportRequestDto;
import ru.aveskin.reportmicroservice.service.ReportGeneratorService;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Tag(name = "Сервис отчетов")
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;

    @Operation(summary = "Получает PDF отчет по истории акции в портфеле, за промежуток времени")
    @GetMapping("/create")
    ResponseEntity<byte[]> createPdfReport(
            @RequestParam String ticker,
            @RequestParam LocalDate dateLow,
            @RequestParam LocalDate dateHigh) {
        try {
            ReportRequestDto request = new ReportRequestDto(ticker, dateLow, dateHigh);

            byte[] pdf = reportGeneratorService.generate(request);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.add("Content-Type", MediaType.APPLICATION_PDF_VALUE);

            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
