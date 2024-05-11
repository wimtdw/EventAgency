package ru.mirea.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.event.domain.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByName(String name);
}
