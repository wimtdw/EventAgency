package ru.minusd.security.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueryDTO {
    private String name;
    private String text;
    private String email;

    // Конструкторы, геттеры и сеттеры
    // ...
}
