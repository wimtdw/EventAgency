package ru.minusd.security.domain.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueryDTO {
    private Long id;
    private String name;
    private String username;
    private String text;
    private String email;
    private String date;
    private String status;

    // Конструкторы, геттеры и сеттеры
    // ...
}
