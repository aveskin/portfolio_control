package ru.aveskin.marketdatamicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.aveskin.marketdatamicroservice.service.StockService;

@RestController
@RequiredArgsConstructor
@EnableAsync
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStockByTicker(@PathVariable String ticker){
        return stockService.getStockByTicker(ticker);
    }

    @PostMapping("/stocks")
    public StocksDto getStocksByTickerList(@RequestBody TickersDto request){
        return stockService.getStocksByTickers(request);
    }


}
