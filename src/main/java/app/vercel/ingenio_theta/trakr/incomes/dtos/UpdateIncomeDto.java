package app.vercel.ingenio_theta.trakr.incomes.dtos;

import app.vercel.ingenio_theta.trakr.incomes.models.IncomeSource;
import app.vercel.ingenio_theta.trakr.shared.validators.valid_enum.IsEnum;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIncomeDto {
    @Min(value = 50, message = "The minimum amount for an income is XAF 50")
    private Double amount;

    private String description;

    @IsEnum(enumClass = IncomeSource.class, message = "income source is not valid")
    private String source;
}
