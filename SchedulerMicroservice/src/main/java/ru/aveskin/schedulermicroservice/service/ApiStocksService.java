package ru.aveskin.schedulermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.aveskin.schedulermicroservice.dto.StockPriceDto;
import ru.aveskin.schedulermicroservice.dto.StockPricesDto;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ApiStocksService {
    private final RestTemplate restTemplate;

    public List<StockPriceDto> getStockPrisesByUidList(List<String> prices) {
        String url = "http://localhost:8088/api/stocks/price_list";

        StockPricesDto request = new StockPricesDto();
        request.setPrices(prices);

        try {
            StockPriceDto[] response = restTemplate.postForObject(url, request, StockPriceDto[].class);
            if (response != null) {
                return List.of(response);
            }
        } catch (RestClientException e) {
            log.error("Error occurred: " + e.getMessage());
        }

        return List.of();
    }
}
