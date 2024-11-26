package ru.aveskin.portfoliomicroservise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aveskin.portfoliomicroservise.dto.UserContext;
import ru.aveskin.portfoliomicroservise.entity.Portfolio;
import ru.aveskin.portfoliomicroservise.entity.User;
import ru.aveskin.portfoliomicroservise.repository.PortfolioRepository;
import ru.aveskin.portfoliomicroservise.repository.UserRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final UserContext userContext;
//    private final PortfolioAssetRepository portfolioAssetRepository;

    @Override
    public Portfolio createPortfolio() {
        User user = userRepository.findById(userContext.getId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setAssets(new ArrayList<>());
        portfolioRepository.save(portfolio);

        return portfolio;
    }

    @Override
    public void deletePortfolio(Long id) {
       Portfolio portfolio = portfolioRepository.findById(id)
               .orElseThrow(() -> new NoSuchElementException("Portfolio with id =  " + id + "  is not found"));
    }
}
