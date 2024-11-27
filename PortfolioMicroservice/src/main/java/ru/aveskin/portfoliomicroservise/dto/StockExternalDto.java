package ru.aveskin.portfoliomicroservise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockExternalDto {
        private String uid;
        private String ticker;
        private String figi;
        private String name;
        private String type;
        private String currency;
        private String source;
}
