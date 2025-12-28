package app.vercel.ingenio_theta.trakr.budgets.dtos;

import app.vercel.ingenio_theta.trakr.shared.dto.PageRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetBudgetsDto extends PageRequestDto {
    private String userId;
}
