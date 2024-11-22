package ru.aveskin.marketdatamicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aveskin.marketdatamicroservice.dto.StockPriceDto;
import ru.aveskin.marketdatamicroservice.dto.StockPricesDto;
import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.aveskin.marketdatamicroservice.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("{ticker}")
    public Stock getStockByTicker(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    @GetMapping("price/{uid}")
    public StockPriceDto getStockPriceByUid(@PathVariable String uid) {
        return stockService.getStockPrice(uid);
    }

    @PostMapping("/stock_list")
    public StocksDto getStocksByTickerList(@RequestBody TickersDto request) {
        return stockService.getStocksByTickers(request);
    }

    @PostMapping("/price_list")
    public List<StockPriceDto> getStockPriceByUidList(@RequestBody StockPricesDto request) {
        return stockService.getStockPrices(request);
    }
}
