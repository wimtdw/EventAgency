package ru.minusd.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minusd.security.domain.model.Query;
import ru.minusd.security.domain.model.User;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByUser(User user);
}