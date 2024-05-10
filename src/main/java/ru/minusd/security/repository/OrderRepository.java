package ru.minusd.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minusd.security.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Методы для работы с заказами
}