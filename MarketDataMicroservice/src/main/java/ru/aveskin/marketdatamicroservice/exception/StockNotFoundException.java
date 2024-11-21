package ru.aveskin.marketdatamicroservice.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String ticker) {
        super(ticker + " not found");
    }
}

