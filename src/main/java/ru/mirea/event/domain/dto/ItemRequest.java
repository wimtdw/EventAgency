package ru.mirea.event.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Мероприятие")
public class ItemRequest {

    @Schema(description = "", example = "Jon")
    private String name;

    @Schema(description = "", example = "")
    private String text;

    @Schema(description = "", example = "")
    private String image;
}
