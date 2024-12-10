package ru.aveskin.portfoliomicroservise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Запрос на добавление оповещения по уровню цены")
public class AddAlertRequestDto {
    @Schema(description = "id портфеля", example = "2")
    @NotBlank(message = "id портфеля быть пустыми")
    private Long portfolioId;

    @Schema(description = "Тикер", example = "GMKN")
    @NotBlank(message = "Тикер не может быть пустыми")
    private String ticker;

    @Schema(description = "Целевая цена", example = "111.111")
    @NotNull(message = "Целевая цена должна быть указана")
    @Positive(message = "Целевая цена должна быть больше 0")
    private BigDecimal aimPrice;
}
