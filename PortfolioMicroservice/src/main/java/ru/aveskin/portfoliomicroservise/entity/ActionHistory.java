package ru.aveskin.portfoliomicroservise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "action_history", schema = "portfolio")
@Getter
@Setter
public class ActionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Column(name = "action")
    private MarketAction action;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
