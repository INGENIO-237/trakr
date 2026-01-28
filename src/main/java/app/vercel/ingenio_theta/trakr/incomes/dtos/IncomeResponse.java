package app.vercel.ingenio_theta.trakr.incomes.dtos;

import java.time.LocalDateTime;

import app.vercel.ingenio_theta.trakr.incomes.models.IncomeSource;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResponse {
    private String id;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private double amount;
    private String description;
    private UserResponse user;
    private IncomeSource source;
}
