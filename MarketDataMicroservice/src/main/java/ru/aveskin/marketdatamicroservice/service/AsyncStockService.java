package ru.aveskin.marketdatamicroservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsyncStockService {

    @Value("${ssotoken.signing.key}")
    private String ssoToken;

    @Async
    public CompletableFuture<Share> getShareByTickerAsync(String ticker) {
        var api = InvestApi.createReadonly(ssoToken);
        Share share;
        try {
            share = api.getInstrumentsService().getShareByTickerSync(ticker, "TQBR");
        } catch (Exception e) {
            log.error(e.getMessage());
            share = null;
        }

        return CompletableFuture.completedFuture(share);
    }
}