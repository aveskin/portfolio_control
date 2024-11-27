package ru.aveskin.portfoliomicroservise.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("not enough money");
    }
}
