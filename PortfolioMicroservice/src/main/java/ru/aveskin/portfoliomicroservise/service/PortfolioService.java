package ru.aveskin.portfoliomicroservise.service;

import ru.aveskin.portfoliomicroservise.dto.*;

import java.math.BigDecimal;

public interface PortfolioService {

    PortfolioResponseDto createPortfolio();

    void deletePortfolio(Long id);

    void increaseDeposit(IncreaseDepositRequestDto requestDto);

    PortfolioResponseDto getPortfolio(Long id);

    PortfolioResponseDto buyStock(BuyStockRequestDto request);

    PortfolioResponseDto sellStock(SellStockRequestDto request);

    void addAlert(AddAlertRequestDto request);
}
