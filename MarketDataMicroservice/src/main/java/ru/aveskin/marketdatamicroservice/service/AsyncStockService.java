package ru.aveskin.marketdatamicroservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncStockService {

    @Value("${ssotoken.signing.key}")
    private String ssoToken;

    @Async
    public CompletableFuture<Share> getShareByTickerAsync(String ticker) {
        var api = InvestApi.createReadonly(ssoToken);
        var share = api.getInstrumentsService().getShareByTickerSync(ticker, "TQBR");
        return CompletableFuture.completedFuture(share);
    }
}