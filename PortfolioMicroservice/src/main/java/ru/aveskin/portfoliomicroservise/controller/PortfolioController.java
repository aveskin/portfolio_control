package ru.aveskin.portfoliomicroservise.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aveskin.portfoliomicroservise.dto.*;
import ru.aveskin.portfoliomicroservise.entity.Portfolio;
import ru.aveskin.portfoliomicroservise.service.PortfolioService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@Tag(name = "Сервис управления портфелем")
@Slf4j
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Operation(summary = "Получает портфель для залогиненого пользователя")
    @GetMapping("/get/{id}")
    ResponseEntity<PortfolioResponseDto> getPortfolio(@PathVariable Long id) {
        PortfolioResponseDto portfolio = portfolioService.getPortfolio(id);
        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }

    @Operation(summary = "Добавляет новый портфель для залогиненого пользователя")
    @PostMapping("/create")
    ResponseEntity<PortfolioResponseDto> createPortfolio() {
        PortfolioResponseDto createdPortfolio = portfolioService.createPortfolio();
        log.info("портфель с id = " + createdPortfolio.getId() + "добавлен: " + LocalDateTime.now());
        return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаляет портфель по Id")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Portfolio> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        log.info("портфель с id = " + id + "удален: " + LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Пополнить счет портфеля")
    @PutMapping("/add_money")
    ResponseEntity<Void> increaseDeposit(@RequestBody @Valid IncreaseDepositRequestDto request) {
        portfolioService.increaseDeposit(request);
        log.info("счет портфеля с id = " + request.getPortfolioId() + "пополнен: " + LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Купить акцию по рыночной цене")
    @PostMapping("/buy")
    ResponseEntity<PortfolioResponseDto> buyStock(@RequestBody @Valid BuyStockRequestDto request) {
        PortfolioResponseDto response = portfolioService.buyStock(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Продать акцию по рыночной цене")
    @PostMapping("/sell")
    ResponseEntity<PortfolioResponseDto> sellStock(@RequestBody @Valid SellStockRequestDto request) {
        PortfolioResponseDto response = portfolioService.sellStock(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Добавление наблюдения за ценой акции(оповещение при достижении цены)")
    @PostMapping("/add_alert")
    ResponseEntity<Void> addAlert(@RequestBody @Valid AddAlertRequestDto request) {
        portfolioService.addAlert(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


}
