package app.vercel.ingenio_theta.trakr.budgets.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse {
    private String id;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private double amount;
    private UserResponse user;
}
