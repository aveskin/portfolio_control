package ru.aveskin.portfoliomicroservise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "portfolio_alerts", schema = "portfolio")
@Getter
@Setter
@EqualsAndHashCode
public class PortfolioAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "aim_price")
    private BigDecimal aimPrice;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "is_positive_trigger")
    private boolean isPositiveTrigger;
}
