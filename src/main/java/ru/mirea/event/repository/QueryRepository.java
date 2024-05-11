package ru.mirea.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.event.domain.model.Query;
import ru.mirea.event.domain.model.User;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByUser(User user);
}