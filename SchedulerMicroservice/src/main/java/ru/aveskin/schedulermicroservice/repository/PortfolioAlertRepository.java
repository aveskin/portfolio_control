package ru.aveskin.schedulermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aveskin.schedulermicroservice.entity.PortfolioAlert;

import java.util.List;

public interface PortfolioAlertRepository extends JpaRepository<PortfolioAlert, Long> {
    @Query("SELECT a FROM PortfolioAlert a WHERE a.isCompleted = false")
    List<PortfolioAlert> findByIsCompletedFalse();

    @Query(value = "SELECT DISTINCT uid FROM portfolio.portfolio_alerts", nativeQuery = true)
    List<String> findDistinctUidList();
}
