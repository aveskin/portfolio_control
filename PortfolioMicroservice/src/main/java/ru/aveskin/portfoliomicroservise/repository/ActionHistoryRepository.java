package ru.aveskin.portfoliomicroservise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aveskin.portfoliomicroservise.entity.ActionHistory;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {
}
