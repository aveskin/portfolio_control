package ru.aveskin.schedulermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.aveskin.schedulermicroservice.dto.AlertTriggeredEvent;
import ru.aveskin.schedulermicroservice.dto.StockPriceDto;
import ru.aveskin.schedulermicroservice.entity.PortfolioAlert;
import ru.aveskin.schedulermicroservice.repository.PortfolioAlertRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceMonitoringService {
    private final KafkaEventSender kafkaEventSender;
    private final PortfolioAlertRepository portfolioAlertRepository;
    private final ApiStocksService apiStocksService;


    @Scheduled(fixedRateString = "${SCHEDULED_FIXED_RATE}")
    public void monitorPrices() {
        log.info("Получение данных из БД");
        List<PortfolioAlert> alerts = portfolioAlertRepository.findByIsCompletedFalse();

        if (alerts.isEmpty()) {
            log.info("Данные не выбраны");
            return;
        }

        log.info("Получаем текущие цены по выбраным данным");
        Map<String, BigDecimal> currentPrices =
                fetchCurrentPrices(portfolioAlertRepository.findDistinctUidList());

        log.info("Проверяем уровни цен");
        List<PortfolioAlert> triggeredTickers = checkPriceLevels(currentPrices, alerts);


        log.info("Отправляем в кафку уведомления для достигнутых цен");
        for (PortfolioAlert alert : triggeredTickers) {
            kafkaEventSender.sendAlertTriggeredEvent(
                    new AlertTriggeredEvent(alert.getId(), alert.getTicker(), alert.getAimPrice()));

            alert.setCompleted(true);
            portfolioAlertRepository.save(alert);
        }


    }

    private List<PortfolioAlert> checkPriceLevels(Map<String, BigDecimal> currentPrices, List<PortfolioAlert> alerts) {
        List<PortfolioAlert> triggeredAlerts = new ArrayList<>();

        for (PortfolioAlert alert : alerts) {
            String uid = alert.getUid();
            BigDecimal aimPrice = alert.getAimPrice();

            if (currentPrices.containsKey(uid)) {
                BigDecimal currentPrice = currentPrices.get(uid);

                if (currentPrice.compareTo(aimPrice) >= 0) {
                    triggeredAlerts.add(alert);
                }
            }
        }
        return triggeredAlerts;
    }

    private Map<String, BigDecimal> fetchCurrentPrices(List<String> distinctUidList) {
        List<StockPriceDto> priceList = apiStocksService.getStockPrisesByUidList(distinctUidList);

        return priceList.stream()
                .collect(Collectors.toMap(StockPriceDto::getUid, StockPriceDto::getPrice));
    }
}