package ru.aveskin.marketdatamicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.aveskin.marketdatamicroservice.service.StockService;

@RestController
@RequiredArgsConstructor
@EnableAsync
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker){
        return stockService.getStockByTicker(ticker);
    }


}
