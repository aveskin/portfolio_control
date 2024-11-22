package ru.aveskin.marketdatamicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.aveskin.marketdatamicroservice.dto.StockPriceDto;
import ru.aveskin.marketdatamicroservice.dto.StockPricesDto;
import ru.aveskin.marketdatamicroservice.dto.StocksDto;
import ru.aveskin.marketdatamicroservice.dto.TickersDto;
import ru.aveskin.marketdatamicroservice.exception.StockNotFoundException;
import ru.aveskin.marketdatamicroservice.model.Stock;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static ru.tinkoff.piapi.contract.v1.LastPriceType.LAST_PRICE_UNSPECIFIED;


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

        return new Stock(
                share.getUid(),
                share.getTicker(),
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
                .map(share -> new Stock(
                        share.getUid(),
                        share.getTicker(),
                        share.getFigi(),
                        share.getName(),
                        share.getShareType().toString(),
                        share.getCurrency(),
                        "TinkoffApi"))
                .collect(Collectors.toList());

        return new StocksDto(stockList);
    }

    @Override
    public StockPriceDto getStockPrice(String uid) {
        var api = InvestApi.createReadonly(ssoToken);
        CompletableFuture<List<LastPrice>> listPriceCF;
        List<LastPrice> price;

        List<String> instrumentIds = new ArrayList<>();
        instrumentIds.add(uid);
        Iterable<String> iterableString = instrumentIds;

        try {
            listPriceCF = api.getMarketDataService().getLastPrices(iterableString, LAST_PRICE_UNSPECIFIED);
            price = listPriceCF.join();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

        BigDecimal totalPrice = buildPrice(
                price.get(0)
                        .getPrice()
                        .getUnits(),
                price.get(0)
                        .getPrice()
                        .getNano());

        return new StockPriceDto(uid, totalPrice);
    }

    @Override
    public List<StockPriceDto> getStockPrices(StockPricesDto uidList) {
        var api = InvestApi.createReadonly(ssoToken);
        CompletableFuture<List<LastPrice>> listPriceCF;
        List<LastPrice> priceList;

        Iterable<String> iterableString = uidList.getPrices();

        try {
            listPriceCF = api.getMarketDataService().getLastPrices(iterableString, LAST_PRICE_UNSPECIFIED);
            priceList = listPriceCF.join();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

        List<StockPriceDto> stockPricesDto = new ArrayList<>();
        priceList.forEach(price -> {
            BigDecimal totalPrice = buildPrice(
                    price.getPrice().getUnits(),
                    price.getPrice().getNano());
            if (price.getPrice().getUnits() != 0 || price.getPrice().getNano() != 0) {
                stockPricesDto.add(new StockPriceDto(price.getInstrumentUid(), totalPrice));
            }
        });

        return stockPricesDto;
    }

    private BigDecimal buildPrice(long priceUnit, int priceNano) {
        BigDecimal unit = BigDecimal.valueOf(priceUnit);
        BigDecimal nano = BigDecimal.valueOf(priceNano).movePointLeft(9);
        return unit.add(nano);
    }

}
