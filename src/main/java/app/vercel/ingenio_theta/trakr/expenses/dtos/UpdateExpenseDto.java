package app.vercel.ingenio_theta.trakr.expenses.dtos;

import app.vercel.ingenio_theta.trakr.expenses.models.ExpenseCategory;
import app.vercel.ingenio_theta.trakr.shared.validators.valid_enum.IsEnum;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExpenseDto {
    @Min(value = 50, message = "The minimum amount for an expense is XAF 50")
    private Double amount;

    private String description;

    @IsEnum(enumClass = ExpenseCategory.class, message = "Expense category is not valid")
    private String category;
}
