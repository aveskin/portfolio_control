package ru.aveskin.schedulermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulerMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerMicroserviceApplication.class, args);
    }

}
