package ru.aveskin.portfoliomicroservise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PortfolioMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioMicroserviceApplication.class, args);
    }

}
