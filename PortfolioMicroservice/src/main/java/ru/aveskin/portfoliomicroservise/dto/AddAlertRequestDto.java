package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddAlertRequestDto {
    private Long portfolioId;
    private String ticker;
    private BigDecimal aimPrice;
}
