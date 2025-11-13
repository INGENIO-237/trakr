package app.vercel.ingenio_theta.trakr.shared.exceptions.core;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private LocalDateTime timestamp;
    @NonNull
    private HttpStatus status;

    public ApiException(@NonNull HttpStatus status, String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
}
