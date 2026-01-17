package app.vercel.ingenio_theta.trakr.expenses.dtos;

import app.vercel.ingenio_theta.trakr.expenses.models.ExpenseCategory;
import app.vercel.ingenio_theta.trakr.shared.validators.valid_enum.IsEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseDto {
        @NotNull(message = "Expense's limit is required")
        @Min(value = 50, message = "The minimum amount for a expense is XAF 50")
        private double amount;

        @NotBlank(message = "Expense's description is required")
        private String description;

        @NotNull(message = "Expense category is required")
        @IsEnum(enumClass = ExpenseCategory.class, message = "Expense category is not valid")
        private String category;
}
