package ru.minusd.security.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // Это создаст колонку user_id в таблице orders
    private User user; // Предполагается, что класс User уже определен и содержит поля id, name, email

    @Column(length = 1000) // Увеличиваем размер колонки для описания
    private String description; // Текст описания пожеланий заказчика

    @ElementCollection
    @CollectionTable(name = "order_additional_services", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "service") // Это создаст таблицу order_additional_services с колонками order_id и service
    private List<String> additionalServices; // Дополнительные услуги

    // Геттеры и сеттеры
}


