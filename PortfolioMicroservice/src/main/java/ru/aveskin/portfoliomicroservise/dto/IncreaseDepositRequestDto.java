package ru.aveskin.portfoliomicroservise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Запрос на увеличение депозита")
public class IncreaseDepositRequestDto {
    @Schema(description = "id портфеля", example = "2")
    @NotBlank(message = "id портфеля быть пустыми")
    private Long portfolioId;

    @Schema(description = "Сумма в рублях", example = "10000")
    @NotNull(message = "Сумма должна быть указана")
    @Positive(message = "Количество акций должно быть больше 0")
    private BigDecimal amount;
}
