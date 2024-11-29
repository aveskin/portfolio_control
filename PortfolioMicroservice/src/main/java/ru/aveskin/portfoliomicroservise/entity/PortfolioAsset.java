package ru.aveskin.portfoliomicroservise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "portfolio_assets", schema = "portfolio")
@Getter
@Setter
public class PortfolioAsset {

    @Id
    private String uid;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "average_price")
    private BigDecimal averagePrice;

}