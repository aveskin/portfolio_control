package ru.aveskin.reportmicroservice.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReportContent {
    private Long portfolioId;
    private String uid;
    private String ticker;
    private String name;
    private Integer quantity;
    private BigDecimal currentPrice;
    private MarketAction action;
    private LocalDateTime createdAt;
}
