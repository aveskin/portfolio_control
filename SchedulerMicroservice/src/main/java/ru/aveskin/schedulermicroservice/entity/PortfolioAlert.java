package ru.aveskin.schedulermicroservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "portfolio_alerts", schema = "portfolio")
public class PortfolioAlert {
    @Id
    private Long id;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "aim_price")
    private BigDecimal aimPrice;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "uid")
    private String uid;

    @Column(name = "is_positive_triger")
    private boolean isPositiveTrigger;
}
