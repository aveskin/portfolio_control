package ru.aveskin.portfoliomicroservise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aveskin.portfoliomicroservise.entity.ActionHistory;
@Repository
public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {
}
