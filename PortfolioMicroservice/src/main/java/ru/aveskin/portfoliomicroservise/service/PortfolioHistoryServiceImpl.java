package ru.aveskin.portfoliomicroservise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aveskin.portfoliomicroservise.dto.BuyStockRequestDto;
import ru.aveskin.portfoliomicroservise.dto.SellStockRequestDto;
import ru.aveskin.portfoliomicroservise.dto.StockExternalDto;
import ru.aveskin.portfoliomicroservise.dto.StockPriceExternalDto;
import ru.aveskin.portfoliomicroservise.entity.ActionHistory;
import ru.aveskin.portfoliomicroservise.entity.MarketAction;
import ru.aveskin.portfoliomicroservise.repository.ActionHistoryRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PortfolioHistoryServiceImpl implements PortfolioHistoryService {
    private final ActionHistoryRepository actionHistoryRepository;

    @Override
    public void setBuyHistory(BuyStockRequestDto request,
                              StockPriceExternalDto stockPrice,
                              StockExternalDto stockData,
                              MarketAction action) {
        ActionHistory history = new ActionHistory();
        history.setPortfolioId(request.getPortfolioId());
        history.setUid(stockData.getUid());
        history.setTicker(stockData.getTicker());
        history.setName(stockData.getName());
        history.setQuantity(request.getQuantity());
        history.setCurrentPrice(stockPrice.getPrice());
        history.setAction(action);
        history.setCreatedAt(LocalDateTime.now());

        actionHistoryRepository.save(history);
    }

    @Override
    public void setSellHistory(SellStockRequestDto request,
                               StockPriceExternalDto stockPrice,
                               StockExternalDto stockData,
                               MarketAction action) {
        ActionHistory history = new ActionHistory();
        history.setPortfolioId(request.getPortfolioId());
        history.setUid(stockData.getUid());
        history.setTicker(stockData.getTicker());
        history.setName(stockData.getName());
        history.setQuantity(request.getQuantity());
        history.setCurrentPrice(stockPrice.getPrice());
        history.setAction(action);
        history.setCreatedAt(LocalDateTime.now());

        actionHistoryRepository.save(history);
    }

}

