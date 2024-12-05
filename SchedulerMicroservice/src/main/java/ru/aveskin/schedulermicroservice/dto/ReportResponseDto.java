package ru.aveskin.schedulermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportResponseDto {
    private String email;
    private String pdf;
}
