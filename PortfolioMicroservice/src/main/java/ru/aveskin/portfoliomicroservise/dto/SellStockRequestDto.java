package ru.aveskin.portfoliomicroservise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на продажу акции")
public class SellStockRequestDto {
    @Schema(description = "id портфеля", example = "2")
    @NotBlank(message = "id портфеля быть пустыми")
    private Long portfolioId;

    @Schema(description = "Тикер", example = "GMKN")
    @NotBlank(message = "Тикер не может быть пустыми")
    private String ticker;

    @Schema(description = "Колличество акций", example = "3")
    @NotNull(message = "Колличество акций должно быть указано")
    @Positive(message = "Колличество акций должно быть больше 0")
    private Integer quantity;
}
