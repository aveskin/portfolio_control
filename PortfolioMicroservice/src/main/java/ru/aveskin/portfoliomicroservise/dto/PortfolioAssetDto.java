package ru.aveskin.portfoliomicroservise.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import ru.aveskin.portfoliomicroservise.entity.Portfolio;

import java.math.BigDecimal;

@Data
public class PortfolioAssetDto {
    private String uid;
    private String ticker;
    private String name;
    private Integer quantity;
    private BigDecimal averagePrice;
}
