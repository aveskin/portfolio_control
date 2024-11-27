package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyStockRequestDto {
    private Long portfolioId;
    private String ticker;
    private Integer quantity;
}
