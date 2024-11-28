package ru.aveskin.portfoliomicroservise.exception;

public class NotContainStockException extends RuntimeException {
    public NotContainStockException(String ticker) {
        super("your portfolio does not contain: " + ticker );
    }
}
