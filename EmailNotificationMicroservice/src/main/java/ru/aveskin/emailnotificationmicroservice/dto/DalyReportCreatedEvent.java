package ru.aveskin.emailnotificationmicroservice.dto;

import lombok.Data;

@Data
public class DalyReportCreatedEvent {
    private String email;
    private String pdf;
}
