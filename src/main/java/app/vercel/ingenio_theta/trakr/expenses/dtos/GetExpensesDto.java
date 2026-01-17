package app.vercel.ingenio_theta.trakr.expenses.dtos;

import app.vercel.ingenio_theta.trakr.shared.dto.PageRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetExpensesDto extends PageRequestDto {
    private String userId;
}
