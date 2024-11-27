package ru.aveskin.portfoliomicroservise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.aveskin.portfoliomicroservise.dto.StockExternalDto;
import ru.aveskin.portfoliomicroservise.dto.StockPriceExternalDto;
import ru.aveskin.portfoliomicroservise.exception.ExternalApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiStocksService {
    private final RestTemplate restTemplate;

    //TODO добавить кеширование
    public StockExternalDto getStockByTicker(String ticker) {
        String url = "http://localhost:8088/api/stocks/" + ticker;
        StockExternalDto stockData;

        try {
           stockData = restTemplate.getForObject(url, StockExternalDto.class);
        } catch (HttpClientErrorException e) {
            log.error("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new ExternalApiException(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new ExternalApiException(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            log.error("Resource access error: " + e.getMessage());
            throw new ExternalApiException(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred: " + e.getMessage());
            throw new ExternalApiException(e.getMessage());
        }
        return stockData;
    }

    public StockPriceExternalDto getStockPriceByUid(String uid) {
        String url = "http://localhost:8088/api/stocks/price/" + uid;
        StockPriceExternalDto stockPrice;

        try {
            stockPrice = restTemplate.getForObject(url, StockPriceExternalDto.class);
        } catch (HttpClientErrorException e) {
            log.error("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new ExternalApiException(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new ExternalApiException(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            log.error("Resource access error: " + e.getMessage());
            throw new ExternalApiException(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred: " + e.getMessage());
            throw new ExternalApiException(e.getMessage());
        }
        return stockPrice;

    }

//    public ResponseEntity<String> createData(Object newData) {
//        String url = "https://api.example.com/data";
//        return restTemplate.postForEntity(url, newData, String.class);
//    }

}
