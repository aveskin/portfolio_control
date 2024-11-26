package ru.aveskin.portfoliomicroservise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aveskin.portfoliomicroservise.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
