package ru.aveskin.schedulermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.aveskin.schedulermicroservice.dto.ReportResponseDto;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiReportService {
    private final RestTemplate restTemplate;

    public ReportResponseDto getDailyReport() {
        LocalDate currentDate = LocalDate.now();
        String email = "test@yandex.ru";

        //TODO добавить получение списка EMAIL, для рассылки, пока 1 email

        String filter = "?dateLow="+currentDate+"&dateHigh="+currentDate+"email="+email;
        String url = "http://localhost:8033/api/report/create" + filter;

        try {
            return restTemplate.getForObject(url, ReportResponseDto.class);
        } catch (RestClientException e) {
            log.error("Error occurred: " + e.getMessage());
        }
        return null;
    }
}
