package ru.aveskin.portfoliomicroservise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aveskin.portfoliomicroservise.dto.*;
import ru.aveskin.portfoliomicroservise.entity.MarketAction;
import ru.aveskin.portfoliomicroservise.entity.Portfolio;
import ru.aveskin.portfoliomicroservise.entity.PortfolioAsset;
import ru.aveskin.portfoliomicroservise.entity.User;
import ru.aveskin.portfoliomicroservise.exception.NotContainStockException;
import ru.aveskin.portfoliomicroservise.exception.NotEnoughMoneyException;
import ru.aveskin.portfoliomicroservise.exception.NotEnoughStocksException;
import ru.aveskin.portfoliomicroservise.repository.PortfolioRepository;
import ru.aveskin.portfoliomicroservise.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final UserContext userContext;
    private final ApiStocksService apiStocksService;
    private final PortfolioHistoryService portfolioHistoryService;

    @Override
    @Transactional
    public PortfolioResponseDto createPortfolio() {
        User user = userRepository.findById(userContext.getId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setAssets(new ArrayList<>());
        Portfolio newPortfolio = portfolioRepository.save(portfolio);

        return new PortfolioResponseDto(newPortfolio.getId(),
                newPortfolio.getUser().getId(),
                newPortfolio.getUser().getFirstName(),
                new BigDecimal(10_000),
                new ArrayList<>());
    }

    @Override
    @Transactional
    public void deletePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Portfolio with id =  " + id + "  is not found"));

        portfolioRepository.delete(portfolio);
    }

    @Override
    @Transactional
    public void increaseDeposit(IncreaseDepositRequestDto requestDto) {
        Portfolio portfolio = portfolioRepository
                .findById(requestDto.getPortfolioId())
                .orElseThrow(() -> new NoSuchElementException("Portfolio with id =  " +
                        requestDto.getPortfolioId() + "  is not found"));

        portfolio.setDeposit(portfolio.getDeposit().add(requestDto.getAmount()));
        portfolioRepository.save(portfolio);
    }

    @Override
    @Transactional(readOnly = true)
    public PortfolioResponseDto getPortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Portfolio with id =  " + id + "  is not found"));
        return getPortfolioResponseDto(portfolio);
    }

    @Override
    @Transactional
    public PortfolioResponseDto buyStock(BuyStockRequestDto request) {
        Portfolio portfolio = portfolioRepository.findById(request.getPortfolioId())
                .orElseThrow(() ->
                        new NoSuchElementException("Portfolio with id =  " + request.getPortfolioId() + "  is not found"));

        StockExternalDto stockData = apiStocksService.getStockByTicker(request.getTicker());
        StockPriceExternalDto stockPrice = apiStocksService.getStockPriceByUid(stockData.getUid());
        BigDecimal totalPrice = stockPrice.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        int comparison = portfolio.getDeposit()
                .compareTo(totalPrice);
        if (comparison < 0) {
            throw new NotEnoughMoneyException();
        }

        portfolio.setDeposit(portfolio.getDeposit().subtract(totalPrice));
        modifyPortfolioAsset(portfolio, stockPrice, stockData, request, totalPrice);

        portfolioRepository.save(portfolio);

        portfolioHistoryService.setBuyHistory(request, stockPrice, stockData, MarketAction.BUY);

        return getPortfolioResponseDto(portfolio);
    }

    @Override
    @Transactional
    public PortfolioResponseDto sellStock(SellStockRequestDto request) {
        Portfolio portfolio = portfolioRepository.findById(request.getPortfolioId())
                .orElseThrow(() ->
                        new NoSuchElementException("Portfolio with id =  " + request.getPortfolioId() + "  is not found"));

        StockExternalDto stockData = apiStocksService.getStockByTicker(request.getTicker());
        StockPriceExternalDto stockPrice = apiStocksService.getStockPriceByUid(stockData.getUid());
        BigDecimal totalPrice = stockPrice.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        PortfolioAsset relevantAsset = getAvailableAsset(portfolio, stockPrice, stockData, request);

        portfolio.setDeposit(portfolio.getDeposit().add(totalPrice));
        relevantAsset.setQuantity(relevantAsset.getQuantity() - request.getQuantity());
        if (relevantAsset.getQuantity() == 0) {
            relevantAsset.setAveragePrice(BigDecimal.valueOf(0));
        }

        portfolioRepository.save(portfolio);

        portfolioHistoryService.setSellHistory(request, stockPrice, stockData, MarketAction.SELL);

        return getPortfolioResponseDto(portfolio);
    }

    private boolean isContainUid(Portfolio portfolio, String uid) {
        List<PortfolioAsset> assets = portfolio.getAssets();
        return assets != null && assets.stream()
                .anyMatch(asset -> uid.equals(asset.getUid()));
    }

    private PortfolioAsset getAvailableAsset(Portfolio portfolio,
                                             StockPriceExternalDto stockPrice,
                                             StockExternalDto stockData,
                                             SellStockRequestDto request) {
        var assets = portfolio.getAssets();
        if (!isContainUid(portfolio, stockData.getUid())) {
            throw new NotContainStockException(stockData.getTicker());
        }
        PortfolioAsset relevantAsset = assets.stream()
                .filter(asset -> stockData.getUid().equals(asset.getUid()))
                .findFirst()
                .orElseThrow(() -> new NotContainStockException(request.getTicker()));

        int comparison = request.getQuantity()
                .compareTo(relevantAsset.getQuantity());
        if (comparison > 0) {
            throw new NotEnoughStocksException();
        }
        return relevantAsset;
    }

    private PortfolioResponseDto getPortfolioResponseDto(Portfolio portfolio) {
        List<PortfolioAssetDto> assetsDto = portfolio.getAssets().stream()
                .map(asset -> {
                    PortfolioAssetDto dto = new PortfolioAssetDto();
                    dto.setUid(asset.getUid());
                    dto.setTicker(asset.getTicker());
                    dto.setName(asset.getName());
                    dto.setQuantity(asset.getQuantity());
                    dto.setAveragePrice(asset.getAveragePrice());
                    return dto;
                })
                .collect(Collectors.toList());

        return new PortfolioResponseDto(portfolio.getId(),
                portfolio.getUser().getId(),
                portfolio.getUser().getFirstName(),
                portfolio.getDeposit(),
                assetsDto);
    }

    private void modifyPortfolioAsset(Portfolio portfolio,
                                      StockPriceExternalDto stockPrice,
                                      StockExternalDto stockData,
                                      BuyStockRequestDto request,
                                      BigDecimal totalPrice) {
        var assets = portfolio.getAssets();
        if (!isContainUid(portfolio, stockData.getUid())) {
            PortfolioAsset newAsset = new PortfolioAsset();
            newAsset.setUid(stockData.getUid());
            newAsset.setPortfolio(portfolio);
            newAsset.setName(stockData.getName());
            newAsset.setTicker(stockData.getTicker());
            newAsset.setQuantity(request.getQuantity());
            newAsset.setAveragePrice(totalPrice);

            assets.add(newAsset);
            portfolio.setAssets(assets);
        } else {
            PortfolioAsset relevantAsset = assets.stream()
                    .filter(asset -> stockData.getUid().equals(asset.getUid()))
                    .findFirst()
                    .orElseThrow(() -> new NotContainStockException(request.getTicker()));

            BigDecimal currentAveragePrice = relevantAsset.getAveragePrice();

            BigDecimal newAveragePrice = currentAveragePrice
                    .add(stockPrice.getPrice())
                    .divide(BigDecimal.valueOf(2));

            relevantAsset.setAveragePrice(newAveragePrice);
            relevantAsset.setQuantity(relevantAsset.getQuantity() + request.getQuantity());
        }
    }
}
