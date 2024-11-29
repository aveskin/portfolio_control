package ru.aveskin.portfoliomicroservise.service;

import ru.aveskin.portfoliomicroservise.dto.BuyStockRequestDto;
import ru.aveskin.portfoliomicroservise.dto.SellStockRequestDto;
import ru.aveskin.portfoliomicroservise.dto.StockExternalDto;
import ru.aveskin.portfoliomicroservise.dto.StockPriceExternalDto;
import ru.aveskin.portfoliomicroservise.entity.MarketAction;

public interface PortfolioHistoryService {
    void setBuyHistory(BuyStockRequestDto request,
                    StockPriceExternalDto stockPrice,
                    StockExternalDto stockData,
                    MarketAction action);

    void setSellHistory(SellStockRequestDto request,
                    StockPriceExternalDto stockPrice,
                    StockExternalDto stockData,
                    MarketAction action);
}
