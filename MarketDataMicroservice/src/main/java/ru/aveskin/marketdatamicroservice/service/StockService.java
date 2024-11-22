package ru.aveskin.marketdatamicroservice.service;

import ru.aveskin.marketdatamicroservice.dto.StockPriceDto;
import ru.aveskin.marketdatamicroservice.dto.StockPricesDto;
import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.model.Stock;

import java.util.List;

public interface StockService {
    Stock getStockByTicker(String ticker);

    StocksDto getStocksByTickers(TickersDto tickers);

    StockPriceDto getStockPrice(String uid);

    List<StockPriceDto> getStockPrices(StockPricesDto uidList);

}
