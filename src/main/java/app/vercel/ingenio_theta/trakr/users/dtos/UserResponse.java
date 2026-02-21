package app.vercel.ingenio_theta.trakr.users.dtos;

import app.vercel.ingenio_theta.trakr.shared.dto.BaseEntityResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseEntityResponse {
    private String name;
    private String email;
}
