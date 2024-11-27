package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockPriceExternalDto {
    private String uid;
    private BigDecimal price;
}
