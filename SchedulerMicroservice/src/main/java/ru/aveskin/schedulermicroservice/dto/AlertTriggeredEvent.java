package ru.aveskin.schedulermicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertTriggeredEvent {
    private Long id;
    private String ticker;
    private BigDecimal aimPrice;
}
