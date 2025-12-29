package app.vercel.ingenio_theta.trakr.budgets.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBudgetDto {
    @FutureOrPresent(message = "Budget's start date cannot be a past date")
    private LocalDate startDate;

    @Future(message = "Budget's end date must be a future one")
    private LocalDate endDate;

    @Min(value = 10000, message = "The minimum amount for a budget is XAF 10,000")
    private Double amount;
}
