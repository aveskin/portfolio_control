package ru.aveskin.marketdatamicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.aveskin.marketdatamicroservice.dto.StockPriceDto;
import ru.aveskin.marketdatamicroservice.dto.StockPricesDto;
import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.aveskin.marketdatamicroservice.service.StockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@EnableAsync
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStockByTicker(@PathVariable String ticker){
        return stockService.getStockByTicker(ticker);
    }

    @GetMapping("/stocks/price/{uid}")
    public StockPriceDto getStockPriceByUid(@PathVariable String uid){
        return stockService.getStockPrice(uid);
    }

    @PostMapping("/stocks")
    public StocksDto getStocksByTickerList(@RequestBody TickersDto request){
        return stockService.getStocksByTickers(request);
    }

    @PostMapping("/stocks/prices")
    public List<StockPriceDto> getStockPriceByUidList(@RequestBody StockPricesDto request){
        return stockService.getStockPrices(request);
    }


}
