package ru.aveskin.emailnotificationmicroservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertTriggeredEvent {
    private Long id;
    private String userEmail;
    private String ticker;
    private BigDecimal aimPrice;
}
