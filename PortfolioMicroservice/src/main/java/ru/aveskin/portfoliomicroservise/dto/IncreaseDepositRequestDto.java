package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IncreaseDepositRequestDto {
    private Long portfolioId;
    private BigDecimal amount;
}
