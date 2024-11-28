package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StockExternalDto implements Serializable {
        private String uid;
        private String ticker;
        private String figi;
        private String name;
        private String type;
        private String currency;
        private String source;
}
