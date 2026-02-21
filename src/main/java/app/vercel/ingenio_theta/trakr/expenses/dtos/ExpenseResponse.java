package app.vercel.ingenio_theta.trakr.expenses.dtos;

import app.vercel.ingenio_theta.trakr.expenses.models.ExpenseCategory;
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
public class ExpenseResponse extends BaseEntityResponse {
    private double amount;
    private String description;
    private UserResponse user;
    private ExpenseCategory category;
}
