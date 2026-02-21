package app.vercel.ingenio_theta.trakr.budgets.dtos;

import java.time.LocalDate;

import app.vercel.ingenio_theta.trakr.shared.dto.BaseEntityResponse;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse extends BaseEntityResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private double amount;
    private UserResponse user;
}
