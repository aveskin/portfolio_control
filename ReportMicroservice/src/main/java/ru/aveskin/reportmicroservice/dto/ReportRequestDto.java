package ru.aveskin.reportmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReportRequestDto {
    private String ticker;
    private LocalDate dateLow;
    private LocalDate dateHigh;
}
