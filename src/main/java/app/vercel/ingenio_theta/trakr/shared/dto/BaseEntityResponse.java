package app.vercel.ingenio_theta.trakr.shared.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityResponse {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
