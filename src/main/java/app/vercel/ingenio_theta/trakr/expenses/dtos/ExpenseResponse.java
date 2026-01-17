package app.vercel.ingenio_theta.trakr.expenses.dtos;

import java.time.LocalDateTime;

import app.vercel.ingenio_theta.trakr.expenses.models.ExpenseCategory;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double amount;
    private String description;
    private UserResponse user;
    private ExpenseCategory category;
}
