package ru.aveskin.emailnotificationmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmailNotificationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailNotificationMicroserviceApplication.class, args);
    }

}
