package app.vercel.ingenio_theta.trakr.users.dtos;

import io.micrometer.common.lang.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    @Nullable
    private String country;
}
