package app.vercel.ingenio_theta.trakr.budgets.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBudgetDto {
        @NotNull(message = "Budget's start date is required")
        @FutureOrPresent(message = "Budget's start date cannot be a past date")
        private LocalDate startDate;

        @NotNull(message = "Budget's end date is required")
        @Future(message = "Budget's end date must be a future one")
        private LocalDate endDate;

        @NotNull(message = "Budget's limit is required")
        @Min(value = 10000, message = "The minimum amount for a budget is XAF 10,000")
        private double amount;
}
