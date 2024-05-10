package ru.minusd.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minusd.security.domain.model.Query;

public interface QueryRepository extends JpaRepository<Query, Long> {
    // Методы для работы с заказами
}