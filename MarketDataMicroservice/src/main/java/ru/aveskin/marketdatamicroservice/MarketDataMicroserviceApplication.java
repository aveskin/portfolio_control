package ru.aveskin.marketdatamicroservice;

import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MarketDataMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketDataMicroserviceApplication.class, args);
    }

}
