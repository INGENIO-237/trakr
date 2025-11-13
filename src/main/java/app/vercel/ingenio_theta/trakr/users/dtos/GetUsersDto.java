package app.vercel.ingenio_theta.trakr.users.dtos;

import app.vercel.ingenio_theta.trakr.shared.dto.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetUsersDto extends PageRequestDto {
    private String country;
    private String email;
}
