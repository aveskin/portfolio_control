package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class StockPriceExternalDto implements Serializable {
    private String uid;
    private BigDecimal price;
}
