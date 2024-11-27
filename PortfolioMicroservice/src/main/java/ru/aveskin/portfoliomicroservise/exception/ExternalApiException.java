package ru.aveskin.portfoliomicroservise.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String msg) {
        super("Problems with external service: " + msg);
    }
}
