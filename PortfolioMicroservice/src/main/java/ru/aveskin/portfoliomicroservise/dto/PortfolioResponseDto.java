package ru.aveskin.portfoliomicroservise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.aveskin.portfoliomicroservise.entity.PortfolioAsset;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PortfolioResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private BigDecimal deposit;
    private List<PortfolioAssetDto> assets;
}
