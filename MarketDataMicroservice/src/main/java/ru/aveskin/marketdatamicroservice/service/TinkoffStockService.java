package ru.aveskin.marketdatamicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.exception.StockNotFoundException;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.tinkoff.piapi.contract.v1.Share;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TinkoffStockService implements StockService {
    @Value("${ssotoken.signing.key}")
    private String ssoToken;

    private final AsyncStockService asyncStockService;

    @Override
    public Stock getStockByTicker(String ticker) {
        var shareCF = asyncStockService.getShareByTickerAsync(ticker);
        var share = shareCF.join();
        if (share == null) {
            throw new StockNotFoundException(ticker);
        }

        return new Stock(share.getTicker(),
                share.getFigi(),
                share.getName(),
                share.getShareType().toString(),
                share.getCurrency(),
                "TinkoffApi");
    }

    public StocksDto getStocksByTickers(TickersDto tickers) {
        List<CompletableFuture<Share>> markerStocks = new ArrayList<>();
        tickers.getTickers().forEach(ticker -> {
                var shareCF = asyncStockService.getShareByTickerAsync(ticker);
                markerStocks.add(shareCF);
        });

        // Используем allOf для ожидания завершения всех CompletableFuture
        CompletableFuture<Void> allOf = CompletableFuture.allOf(markerStocks.toArray(new CompletableFuture[0]));

        allOf.join(); // Блокируем текущий поток до завершения всех задач

        List<Stock> stockList = markerStocks.stream()
                .map(CompletableFuture::join) // Получаем Share из каждого CompletableFuture
                .filter(Objects::nonNull)
                .map(share -> new Stock(share.getTicker(),
                        share.getFigi(),
                        share.getName(),
                        share.getShareType().toString(),
                        share.getCurrency(),
                        "TinkoffApi"))
                .collect(Collectors.toList());

        return new StocksDto(stockList);
    }
}
