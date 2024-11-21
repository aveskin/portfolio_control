package ru.aveskin.marketdatamicroservice.service;

import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.tinkoff.piapi.core.InvestApi;

public interface StockService {
    Stock getStockByTicker(String ticker);

    StocksDto getStocksByTickers(TickersDto tickers);

}
