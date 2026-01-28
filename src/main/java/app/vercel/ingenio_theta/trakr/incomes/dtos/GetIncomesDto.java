package app.vercel.ingenio_theta.trakr.incomes.dtos;

import app.vercel.ingenio_theta.trakr.shared.dto.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GetIncomesDto extends PageRequestDto {
}
