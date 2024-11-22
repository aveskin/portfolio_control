package ru.aveskin.marketdatamicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class Stock {
    private String uid;
    private String ticker;
    private String figi;
    private String name;
    private String type;
    private String currency;
    private String source;
}
