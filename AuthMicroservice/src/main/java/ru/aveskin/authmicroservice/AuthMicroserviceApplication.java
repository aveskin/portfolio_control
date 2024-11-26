package ru.aveskin.authmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AuthMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthMicroserviceApplication.class, args);
    }

}
