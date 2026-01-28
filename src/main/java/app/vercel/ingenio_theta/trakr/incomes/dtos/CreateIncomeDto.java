package app.vercel.ingenio_theta.trakr.incomes.dtos;

import app.vercel.ingenio_theta.trakr.incomes.models.IncomeSource;
import app.vercel.ingenio_theta.trakr.shared.validators.valid_enum.IsEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncomeDto {
        @NotNull(message = "Income's limit is required")
        @Min(value = 50, message = "The minimum amount for a income is XAF 50")
        private double amount;

        private String description;

        @NotNull(message = "Income source is required")
        @IsEnum(enumClass = IncomeSource.class, message = "Income source is not valid")
        private String source;
}
