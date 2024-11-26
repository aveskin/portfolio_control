package ru.aveskin.portfoliomicroservise.service;

import ru.aveskin.portfoliomicroservise.entity.Portfolio;

public interface PortfolioService {

    Portfolio createPortfolio();

    void deletePortfolio(Long id);
}
