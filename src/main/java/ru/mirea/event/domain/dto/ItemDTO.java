package ru.mirea.event.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String name;
    private String text;
    private String image;
}

