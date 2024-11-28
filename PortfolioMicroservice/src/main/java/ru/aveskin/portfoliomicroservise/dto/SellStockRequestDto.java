package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellStockRequestDto {
    private Long portfolioId;
    private String ticker;
    private Integer quantity;
}
