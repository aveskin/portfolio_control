package ru.aveskin.portfoliomicroservise.exception;

public class NotEnoughStocksException extends RuntimeException {
    public NotEnoughStocksException() {
        super("not enough stocks for selling");
    }
}
