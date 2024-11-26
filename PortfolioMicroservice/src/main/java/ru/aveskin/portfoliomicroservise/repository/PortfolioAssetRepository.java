package ru.aveskin.portfoliomicroservise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aveskin.portfoliomicroservise.entity.PortfolioAsset;

@Repository
public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset, Long> {
}
